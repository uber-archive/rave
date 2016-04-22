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

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableList;
import com.ubercab.rave.annotation.Validated;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Process the annotations for {@link Validated} annotations.
 */
@AutoService(Processor.class)
public final class RaveProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private Types typesUtils;
    private TypeMirror factoryTypeMirror;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>(2);
        annotations.add(Validated.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        typesUtils = processingEnv.getTypeUtils();
        if (roundEnv.processingOver()) {
            return false;
        }

        Collection<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(Validated.class);
        List<TypeElement> annotatedTypes = new ImmutableList.Builder<TypeElement>()
                .addAll(ElementFilter.typesIn(annotatedElements)).build();
        if (annotatedElements.isEmpty()) {
            return false;
        }

        try {
            RaveIR raveIR = verify(annotatedTypes);
            process(raveIR, annotatedTypes);
            generateSource(raveIR);
        } catch (AbortProcessingException e) {
            return false;
        } catch (Throwable e) {
            error(null, "Error in " + getClass().getSimpleName() + ":" + e);
        }
        return false;
    }

    /**
     * Generate the {@link com.ubercab.rave.BaseValidator} class.
     *
     * @param raveIR the {@link RaveIR} to generate the java.
     * @throws IOException if somethings fails when writing the file.
     */
    private void generateSource(RaveIR raveIR) throws IOException {
        Filer filer = processingEnv.getFiler();
        RaveWriter raveWriter = new RaveWriter(filer, typesUtils);
        raveWriter.generateJava(raveIR);
    }

    /**
     * Process only the {@link Validated} annotations.
     *
     * @param raveIR the IR that will be filled in with required information collected during processing.
     * @param allTypes the elements annotated with the {@link Validated} annotation.
     */
    private void process(@NonNull RaveIR raveIR, @NonNull List<TypeElement> allTypes) {
        for (TypeElement typeElement : allTypes) {
            raveIR.addClassIR(extractClassInfo(typeElement));
        }
    }

    /**
     * Extract the class information from a class annotated with the {@link Validated} annotation. This will create a
     * {@link ClassIR} object that contains all the relevant information about this class to generate the required
     * java code for validation.
     *
     * @param typeElement The {@link TypeElement} annotated with the {@link Validated} annotation.
     * @return the {@link ClassIR} object representing the type element.
     */
    @NonNull
    private ClassIR extractClassInfo(@NonNull TypeElement typeElement) {
        ClassIR classIR = new ClassIR(typesUtils.erasure(typeElement.asType()));
        traverseInheritanceTree(typeElement, classIR);
        List<ExecutableElement> methodElements = new ImmutableList.Builder<ExecutableElement>()
                .addAll(ElementFilter.methodsIn(typeElement.getEnclosedElements())).build();
        String classPackage = CompilerUtils.packageNameOf(typeElement);
        boolean samePackage = classPackage.equals(CompilerUtils.packageNameOf(typesUtils.asElement(factoryTypeMirror)));
        for (ExecutableElement executableElement : methodElements) {
            if (!executableElement.getParameters().isEmpty()
                    || executableElement.getModifiers().contains(Modifier.STATIC)) {
                continue;
            }
            if (!executableElement.getModifiers().contains(Modifier.PUBLIC) && !samePackage) {
                continue;
            }
            MethodIR methodIR = new MethodIR(executableElement.getSimpleName().toString());
            for (AnnotationMirror mirror : elementUtils.getAllAnnotationMirrors(executableElement)) {
                String annotationName = mirror.getAnnotationType().toString();
                if (CompilerUtils.annotationsIsSupported(mirror.getAnnotationType().toString())) {
                    Annotation annotation =
                            executableElement.getAnnotation(CompilerUtils.getAnnotation(annotationName));
                    methodIR.addAnnotation(annotation);
                } else {
                    Annotation annotation = extractDefTypeAnnotations(mirror.getAnnotationType().asElement());
                    if (annotation != null) {
                        methodIR.addAnnotation(annotation);
                    }
                }
            }
            classIR.addMethodIR(methodIR);
        }
        return classIR;
    }

    /**
     * This method extracts Def type annotations. These include {@link StringDef} {@link IntDef}. These annotations
     * are extended so there is a extra level of annotation hierarchy traversal that is required.

     * @param element The {@link Element} to check.
     * @return null if this isn't a support annotation type otherwise the annotation.
     */
    @Nullable
    private Annotation extractDefTypeAnnotations(Element element) {
        Annotation annotation = element.getAnnotation(StringDef.class);
        if (annotation != null) {
            return annotation;
        }
        annotation = element.getAnnotation(IntDef.class);
        if (annotation != null) {
            return annotation;
        }
        return null;
    }

    /**
     * Recursively traverses the inheritance tree of this {@link TypeElement}. Follows both extended classes and
     * all inherited interfaces until it hits the first {@link Validated} annotation then the traversal stops.
     *
     * @param typeElement the type element to check.
     * @param classIR the class IR that holds on to the inheritance information collected.
     */
    private void traverseInheritanceTree(@NonNull TypeElement typeElement, @NonNull ClassIR classIR) {
        TypeMirror mirror = typeElement.getSuperclass();
        // Recurse on inherited parent class.
        if (!(mirror instanceof NoType)) {
            TypeElement parentClass = (TypeElement) typesUtils.asElement(mirror);
            if (parentClass.getAnnotation(Validated.class) != null) {
                classIR.addInheritedTypes(parentClass.asType(), typesUtils);
            } else {
                traverseInheritanceTree(parentClass, classIR);
            }
        }
        // Recurse on inherited interfaces.
        for (TypeMirror typeMirror : typeElement.getInterfaces()) {
            TypeElement inheritedInterface = (TypeElement) typesUtils.asElement(typeMirror);
            if (inheritedInterface.getAnnotation(Validated.class) != null) {
                classIR.addInheritedTypes(inheritedInterface.asType(), typesUtils);
                continue;
            }
            traverseInheritanceTree(inheritedInterface, classIR);
        }
    }

    /**
     * Iterate over everything that has a {@link Validated} annotation and check for invalid usages.
     *
     * @param types classes annotated with the {@link Validated} annotation.
     * @return {@link RaveIR} with initial parameters set.
     */
    @NonNull
    private RaveIR verify(@NonNull List<TypeElement> types) {
        AnnotationVerifier verifier = new AnnotationVerifier(messager, elementUtils, typesUtils);
        for (TypeElement type : types) {
            verifier.verify(type);
        }
        factoryTypeMirror = verifier.getSeenFactoryTypeMirror();
        TypeElement element = (TypeElement) typesUtils.asElement(factoryTypeMirror);
        return new RaveIR(CompilerUtils.packageNameOf(element), element.getSimpleName().toString());
    }

    /**
     * Show and print an error.
     *
     * @param e the element with an error.
     * @param msg the error message to show, optionally containing formatting characters.
     * @param args the arguments to the formatting characters.
     */
    private void error(@Nullable Element e, @NonNull String msg, Object... args) {
        if (e == null) {
            messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
        } else {
            messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
        }
    }
}
