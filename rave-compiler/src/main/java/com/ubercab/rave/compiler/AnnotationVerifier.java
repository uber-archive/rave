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

package com.ubercab.rave.compiler;

import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.google.common.collect.ImmutableList;
import com.ubercab.rave.annotation.Validated;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * This base class verifies common annotations not unique to any environment.
 */
class AnnotationVerifier {

    static final String INTDEF_BAD_RETURN_TYPE_ERROR = " does not return an integer or long value";
    static final String FLOAT_RANGE_BAD_RETURN_TYPE_ERROR = " does not return an float or double value";
    static final String INT_RANGE_BAD_RETURN_TYPE_ERROR = " does not return an int or long value";

    @NonNull protected final Messager messager;
    @NonNull protected final Elements elements;
    @NonNull protected final Types types;

    private TypeMirror seenFactoryTypeMirror = null;

    AnnotationVerifier(@NonNull Messager messager, @NonNull Elements elements, @NonNull Types types) {
        this.messager = messager;
        this.types = types;
        this.elements = elements;
    }

    /**
     * @return the {@link TypeMirror} for the {@link com.ubercab.rave.ValidatorFactory}that was seen during the
     * validation pass.
     */
    @NonNull
    TypeMirror getSeenFactoryTypeMirror() {
        return seenFactoryTypeMirror;
    }

    /**
     * This method verifies a single element that is annotated with the {@link Validated} annotation. If verification
     * fails then it will log the error using {@link #messager} and throw an {@link
     * AbortProcessingException}.
     *
     * @param type the {@link TypeElement} being validated.
     */
    void verify(@NonNull TypeElement type) {
        Validated validatedAnnotation = type.getAnnotation(Validated.class);
        if (validatedAnnotation == null) {
            abortWithError("Annotation processor for @" + Validated.class.getSimpleName()
                    + " was invoked with a type that does not have the annotation.", type);
            return;
        }

        if (type.getModifiers().contains(Modifier.PRIVATE)) {
            abortWithError("Class is private. It must be at least package private", type);
        }

        TypeMirror factoryMirror = getTypeMirrorFromAnnotation(validatedAnnotation, type);
        if (type.getModifiers().contains(Modifier.PROTECTED) || type.getModifiers().contains(Modifier.DEFAULT)) {

            if (!CompilerUtils.packageNameOf(type).equals(
                    CompilerUtils.packageNameOf(types.asElement(factoryMirror)))) {
                abortWithError(type.getSimpleName() + " is not visible to " + factoryMirror.toString(), type);
            }
        }

        if (implementsAnnotation(type)) {
            abortWithError("@" + Validated.class.getSimpleName() + " may not be used to implement an "
                    + "annotation interface.", type);
        }

        checkFactoryClass(factoryMirror, type);
        verifyAnnotationConflicts(type);
        verifyAnnotations(type);
    }

    /**
     * Verify the annotations on the methods of a {@link TypeElement}.
     *
     * @param type the {@link TypeElement} to verify.
     */
    private void verifyAnnotations(TypeElement type) {
        List<ExecutableElement> methodElements = new ImmutableList.Builder<ExecutableElement>()
                .addAll(ElementFilter.methodsIn(type.getEnclosedElements())).build();
        for (ExecutableElement executableElement : methodElements) {
            if (!executableElement.getParameters().isEmpty()
                    || executableElement.getModifiers().contains(Modifier.STATIC)) {
                continue;
            }
            // Verify properties about Size annotation.
            Size size = executableElement.getAnnotation(Size.class);
            if (size != null) {
                if (size.multiple() < 1) {
                    abortWithError("Multiple value is less than 1 with value:" + size.multiple() + " on method "
                            + executableElement.getSimpleName(), type);
                }
            }
            // Check float range is used on a return value that is either a float or double
            FloatRange floatRange = executableElement.getAnnotation(FloatRange.class);
            if (floatRange != null) {
                TypeKind kind = executableElement.getReturnType().getKind();
                if (!(kind == TypeKind.FLOAT || kind == TypeKind.DOUBLE)) {
                    abortWithError(executableElement.getSimpleName().toString() + FLOAT_RANGE_BAD_RETURN_TYPE_ERROR,
                            type);
                }
            }
            // Check IntRange is used on a return value that is either a int or long
            IntRange intRange = executableElement.getAnnotation(IntRange.class);
            if (intRange != null) {
                TypeKind kind = executableElement.getReturnType().getKind();
                if (!(kind == TypeKind.INT || kind == TypeKind.LONG)) {
                    abortWithError(executableElement.getSimpleName().toString() + INT_RANGE_BAD_RETURN_TYPE_ERROR,
                            type);
                }
            }
            // Make sure any @IntDef annotation is only on a method that returns an integer
            for (AnnotationMirror mirror : elements.getAllAnnotationMirrors(executableElement)) {
                IntDef annotation = mirror.getAnnotationType().asElement().getAnnotation(IntDef.class);
                TypeKind kind = executableElement.getReturnType().getKind();
                if (annotation != null && !(kind == TypeKind.INT || kind == TypeKind.LONG)) {
                    abortWithError(executableElement.getSimpleName().toString() + INTDEF_BAD_RETURN_TYPE_ERROR, type);
                }
            }
        }
    }

    /**
     * Check to make sure there is only one factory class referenced.
     *
     * @param factoryTypeMirror the {@link TypeMirror} of the {@link com.ubercab.rave.ValidatorFactory}.
     * @param type the {@link TypeElement} of the class that referenced the {@link com.ubercab.rave.ValidatorFactory}.
     */
    private void checkFactoryClass(@NonNull TypeMirror factoryTypeMirror, @NonNull TypeElement type) {
        if (seenFactoryTypeMirror == null) {
            seenFactoryTypeMirror = factoryTypeMirror;
            return;
        }
        if (!types.isSameType(factoryTypeMirror, seenFactoryTypeMirror)) {
            String errorMsg = "More than one factory class referenced by models "
                    + seenFactoryTypeMirror.toString() + " and " + factoryTypeMirror.toString();
            abortWithError(errorMsg, type);
        }
    }

    /**
     * Verify that the annotations on methods in a model are not conflicting.
     *
     * @param typeElement the {@link TypeElement} of the model being verified.
     */
    private void verifyAnnotationConflicts(@NonNull TypeElement typeElement) {
        List<String> annotationList = new LinkedList<>();
        List<ExecutableElement> methodElements = new ImmutableList.Builder<ExecutableElement>()
                .addAll(ElementFilter.methodsIn(typeElement.getEnclosedElements())).build();
        for (ExecutableElement executableElement : methodElements) {
            annotationList.clear();
            for (AnnotationMirror mirror : elements.getAllAnnotationMirrors(executableElement)) {
                String annotationName = mirror.getAnnotationType().toString();
                if (CompilerUtils.annotationsIsSupported(mirror.getAnnotationType().toString())) {
                    for (String a : annotationList) {
                        if (CompilerUtils.areConflicting(CompilerUtils.getAnnotation(a), CompilerUtils
                                .getAnnotation(annotationName))) {
                            abortWithError("Annotations " + annotationName + " cannot be used with " + a, typeElement);
                        }
                    }
                    annotationList.add(annotationName);
                }
            }
        }
    }

    /**
     * Issue a compilation error and abandon the processing of this class. This does not prevent
     * the processing of other classes.
     *
     * @param msg The error message.
     * @param e The element at which the error occurred.
     */
    private void abortWithError(@NonNull String msg, @NonNull Element e) {
        reportError(msg, e);
        throw new AbortProcessingException();
    }

    /**
     * Issue a compilation error.
     *
     * @param msg The error message.
     * @param e The element at which the error occurred.
     */
    private void reportError(@NonNull String msg, @NonNull Element e) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg, e);
    }

    /**
     * Check if given type implements {@link java.lang.annotation.Annotation}.
     *
     * @param type The type.
     * @return {@code true} if given type implements {@link java.lang.annotation.Annotation}, {@code false} otherwise.
     */
    private boolean implementsAnnotation(@NonNull TypeElement type) {
        return types.isAssignable(type.asType(), getTypeMirror(Annotation.class));
    }

    /**
     * Get the {@link javax.lang.model.type.TypeMirror} for given class.
     *
     * @param cls The class.
     * @return The {@link javax.lang.model.type.TypeMirror} for given class.
     */
    private TypeMirror getTypeMirror(@NonNull Class<?> cls) {
        return elements.getTypeElement(cls.getName()).asType();
    }

    /**
     * This method retrieves the {@link TypeMirror} from an annotation.  This is a bit tricky in annotation
     * processing because the class are not loaded so you can't retrieve class objects from the annotation directly.
     * See: http://blog.retep.org/2009/02/13/getting-class-values-from-annotations-in-an-annotationprocessor/
     *
     * @param validated the {@link Validated} annotation.
     * @param type the TypeElement which is used only for error logging.
     * @return the TypeMirror.
     */
    @NonNull
    private TypeMirror getTypeMirrorFromAnnotation(@NonNull Validated validated, @NonNull TypeElement type) {
        try {
            // This should always throw an exception
            validated.factory();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
        reportError("Retrieving class information from annotation did not throw an exception something is wrong.",
                type);
        throw new AbortProcessingException();
    }
}
