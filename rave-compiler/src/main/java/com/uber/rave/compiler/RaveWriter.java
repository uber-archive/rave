// Copyright (c) 2016 Uber Technologies, Inc.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.uber.rave.compiler;

import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.StringDef;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import com.uber.rave.annotation.MustBeFalse;
import com.uber.rave.annotation.MustBeTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Generates the validation classes.
 */
final class RaveWriter {

    // Method names.
    static final String VALIDATE_METHOD_NAME = "validateAs";
    static final String ADD_SUPPORTED_CLASS_METHOD_NAME = "addSupportedClass";
    static final String RE_EVAL_SUPERTYPE_METHOD_NAME = "reEvaluateAsSuperType";
    static final String MERGE_ERROR_METHOD_NAME = "mergeErrors";

    // Arg names.
    static final String VALIDATE_METHOD_ARG_NAME = "object";
    static final String GENERATED_CLASS_POSTFIX = "_Generated_Validator";
    static final Class<? extends Exception> RAVE_INVALID_MODEL_EXCEPTION_CLASS = InvalidModelException.class;
    static final String RAVE_ERROR_ARG_NAME = "raveErrors";
    static final String VALIDATE_METHOD_CLAZZ_ARG_NAME = "clazz";
    static final String VALIDATION_CONTEXT_ARG_NAME = "context";

    private static final ParameterizedTypeName CLASS_PARAMETERIZED_TYPE_NAME =
            ParameterizedTypeName.get(ClassName.get(Class.class),
                    WildcardTypeName.subtypeOf(TypeName.get(Object.class)));

    private static final String GENERATED_COMMENTS = "https://github.com/uber-common/rave";
    private static final AnnotationSpec GENERATED =
            AnnotationSpec.builder(Generated.class)
                    .addMember("value", "$S", RaveProcessor.class.getName())
                    .addMember("comments", "$S", GENERATED_COMMENTS)
                    .build();

    private final Filer filer;
    private final Types typeUtils;
    private final boolean generatedAnnotationAvailable;

    protected RaveWriter(Filer filer, Types typesUtils, Elements elements) {
        this.filer = filer;
        this.typeUtils = typesUtils;
        generatedAnnotationAvailable = elements.getTypeElement("javax.annotation.Generated") != null;
    }

    /**
     * Generates the java code representing the input IR.
     *
     * @param raveIR the input IR
     * @throws IOException if writing fails.
     */
    public void generateJava(RaveIR raveIR) throws IOException {
        // Make the main method that calls the private methods of the different types.
        List<MethodSpec> allMethods = generateSubtypeValidationMethods(raveIR);
        allMethods.add(generateConstructor(raveIR));
        String className = raveIR.getSimpleName() + GENERATED_CLASS_POSTFIX;
        TypeSpec.Builder builder = TypeSpec.classBuilder(className);
        builder.superclass(BaseValidator.class)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethods(allMethods);
        if (generatedAnnotationAvailable) {
            builder.addAnnotation(GENERATED);
        }
        TypeSpec validatorClass = builder.build();
        JavaFile.builder(raveIR.getPackageName(), validatorClass).build().writeTo(filer);
    }

    private MethodSpec generateConstructor(RaveIR raveIR) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        for (ClassIR classIR : raveIR.getClassIRs()) {
            builder.addStatement("$L($T.class)", ADD_SUPPORTED_CLASS_METHOD_NAME, classIR.getTypeMirror());
        }
        builder.addStatement("registerSelf()");
        return builder.build();
    }

    /**
     * Generate the main method the determines which validate call specifically to make. Basically, checks the class of
     * the object passed in, and calls another validation method.
     *
     * @param raveIR all the classes that are annotated.
     * @return a {@link MethodSpec} for the main method.
     */
    private List<MethodSpec> generateSubtypeValidationMethods(RaveIR raveIR) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder(VALIDATE_METHOD_NAME)
                .addException(RAVE_INVALID_MODEL_EXCEPTION_CLASS)
                .addModifiers(Modifier.PROTECTED)
                .returns(void.class)
                .addAnnotation(Override.class)
                .addParameter(ParameterSpec.builder(Object.class, VALIDATE_METHOD_ARG_NAME)
                        .addAnnotation(NonNull.class).build())
                .addParameter(ParameterSpec.builder(CLASS_PARAMETERIZED_TYPE_NAME, VALIDATE_METHOD_CLAZZ_ARG_NAME)
                        .addAnnotation(NonNull.class).build());
        builder.beginControlFlow("if (!$L.isInstance($L))", VALIDATE_METHOD_CLAZZ_ARG_NAME, VALIDATE_METHOD_ARG_NAME);
        builder.addStatement("throw new $T($L.getClass().getCanonicalName() + $S + $L.getCanonicalName())",
                IllegalArgumentException.class, VALIDATE_METHOD_ARG_NAME, "is not of type",
                VALIDATE_METHOD_CLAZZ_ARG_NAME);
        builder.endControlFlow();
        List<MethodSpec> concreteSpecs = new ArrayList<>(raveIR.getNumClasses());
        for (ClassIR classIR : raveIR.getClassIRs()) {
            MethodSpec specificSpec = generateConcreteMethodSpec(classIR);
            concreteSpecs.add(specificSpec);
            builder.beginControlFlow("if ($L.equals($T.class))", VALIDATE_METHOD_CLAZZ_ARG_NAME,
                    classIR.getTypeMirror());
            builder.addStatement("$L(($T) $L)", VALIDATE_METHOD_NAME, classIR.getTypeMirror(),
                    VALIDATE_METHOD_ARG_NAME);
            builder.addStatement("return");
            builder.endControlFlow();
        }
        builder.addStatement(" throw new $T($L.getClass().getCanonicalName() + $S + "
                        + "this.getClass().getCanonicalName())", IllegalArgumentException.class,
                VALIDATE_METHOD_ARG_NAME, " is not supported by validator ");
        List<MethodSpec> allSpecs = new ArrayList<>();
        allSpecs.add(builder.build());
        // Add all the specific validate functions.
        allSpecs.addAll(concreteSpecs);
        return allSpecs;
    }

    /**
     * Generate a {@link MethodSpec} for a specific type. This generates the private method within the generated class
     * that validates the object as a specific type.
     *
     * @param classIR the type to generate a method for.
     * @return the {@link MethodSpec} to validate the given specific class.
     */
    private MethodSpec generateConcreteMethodSpec(ClassIR classIR) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(VALIDATE_METHOD_NAME)
                .addException(RAVE_INVALID_MODEL_EXCEPTION_CLASS)
                .addModifiers(Modifier.PRIVATE)
                .returns(void.class)
                .addParameter(TypeName.get(classIR.getTypeMirror()), VALIDATE_METHOD_ARG_NAME);
        builder.addStatement("$T $L = getValidationContext($T.class)", BaseValidator.ValidationContext.class,
                VALIDATION_CONTEXT_ARG_NAME, classIR.getTypeMirror());
        builder.addStatement("$T<$T> $L = null", List.class, RaveError.class,
                RAVE_ERROR_ARG_NAME);
        for (TypeMirror mirror : classIR.getInheritedTypes()) {
            // Ex: raveErrors = mergeErrors(reEvaluateAsSuperType(ValidateSample2.class, object), raveErrors);
            builder.addStatement("$L = $L($L, $L($T.class, $L))", RAVE_ERROR_ARG_NAME,
                    MERGE_ERROR_METHOD_NAME, RAVE_ERROR_ARG_NAME, RE_EVAL_SUPERTYPE_METHOD_NAME,
                    typeUtils.erasure(mirror), VALIDATE_METHOD_ARG_NAME);
        }
        for (MethodIR methodIR : classIR.getAllMethods()) {
            buildAnnotationChecks(builder, methodIR);
        }
        builder.beginControlFlow("if ($L != null && !$L.isEmpty())", RAVE_ERROR_ARG_NAME,
                RAVE_ERROR_ARG_NAME);
        builder.addStatement("throw new $T($L)", RAVE_INVALID_MODEL_EXCEPTION_CLASS, RAVE_ERROR_ARG_NAME);
        builder.endControlFlow();
        return builder.build();
    }

    private void buildAnnotationChecks(MethodSpec.Builder builder, MethodIR methodIR) {
        // No check needed for Nullable annotation.
        boolean isNullable = !methodIR.hasAnnotation(NonNull.class);
        boolean hasNonNullOrNullable = methodIR.hasAnnotation(NonNull.class) || methodIR.hasAnnotation(Nullable.class);
        AnnotationWriter writer = new AnnotationWriter(builder,
                MethodSpec.methodBuilder(methodIR.getMethodGetterName()).build(), isNullable, hasNonNullOrNullable);
        if (hasNonNullOrNullable && !(methodIR.hasAnnotation(Size.class) || methodIR.hasAnnotation(StringDef.class))) {
            writer.writeNullable();
        }
        if (methodIR.hasAnnotation(Size.class)) {
            writer.write(methodIR.getAnnotation(Size.class));
        }
        if (methodIR.hasAnnotation(MustBeFalse.class)) {
            writer.writeMustBeFalse();
        }
        if (methodIR.hasAnnotation(MustBeTrue.class)) {
            writer.writeMustBeTrue();
        }
        if (methodIR.hasAnnotation(StringDef.class)) {
            writer.write(methodIR.getAnnotation(StringDef.class));
        }
        if (methodIR.hasAnnotation(IntDef.class)) {
            writer.write(methodIR.getAnnotation(IntDef.class));
        }
        if (methodIR.hasAnnotation(IntRange.class)) {
            writer.write(methodIR.getAnnotation(IntRange.class));
        }
        if (methodIR.hasAnnotation(FloatRange.class)) {
            writer.write(methodIR.getAnnotation(FloatRange.class));
        }
    }
}
