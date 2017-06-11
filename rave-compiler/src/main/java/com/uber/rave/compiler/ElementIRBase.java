package com.uber.rave.compiler;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * This is the IR base class that is shared by the {@link MethodIR} and {@link FieldIR}
 */
abstract class ElementIRBase {
    private final String name;
    private final Map<Class<? extends Annotation>, Annotation> annotations;
    /**
     * Create a new element IR object.
     * @param name the name of the element getter that this IR represents.
     */
    ElementIRBase(String name) {
        annotations = new HashMap<>();
        this.name = name;
    }

    /**
     * Adds a annotation to the element.
     * @param annotation the annotation to add.
     */
    void addAnnotation(Annotation annotation) {
        annotations.put(annotation.annotationType(), annotation);
    }

    /**
     * @return whether the annotations map has any of the RAVE supported annotations.
     */
    boolean hasAnyAnnotation() {
        return annotations.size() > 0;
    }

    /**
     * Returns the element name. This is generally the getter element name for some field in the data model class.
     * @return the string representation of the name.
     */
    String getElementName() {
        return name;
    }

    /**
     * A quick lookup to see if this element was annotated with a particular annotation.
     * @param cls the annoation {@link Class}.
     * @return true if this element was annotated with the input annotation, false otherwise.
     */
    boolean hasAnnotation(Class<? extends Annotation> cls) {
        return annotations.containsKey(cls);
    }

    /**
     * Returns the actual annotation. Null if the element doesn't have that particular annotation.
     * @param cls the annotation {@link Class}.
     * @param <A> the generic must extend {@link Annotation}.
     * @return the annotation or null if the element wasn't annotated with it.
     */
    @Nullable
    <A extends Annotation> A getAnnotation(Class<A> cls) {
        return (A) annotations.get(cls);
    }
}
