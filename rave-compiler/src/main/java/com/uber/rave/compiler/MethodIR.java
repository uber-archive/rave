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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the intermediate representation (IR) of a method within a particular data model class. This class holds
 * all the required information to generate a method which verifies a particular field in a data model.
 */
final class MethodIR {
    @NonNull private final Map<Class<? extends Annotation>, Annotation> annotations;
    @NonNull private final String getterName;

    /**
     * Create a new methodir object.
     * @param getterName the name of the method getter that this IR represents.
     */
    MethodIR(@NonNull String getterName) {
        annotations = new HashMap<>();
        this.getterName = getterName;
    }

    /**
     * Adds a annotation to the method.
     * @param annotation the annotation to add.
     */
    void addAnnotation(@NonNull Annotation annotation) {
        annotations.put(annotation.annotationType(), annotation);
    }

    /**
     * @return whether the annotations map has any of the RAVE supported annotations.
     */
    boolean hasAnyAnnotation() {
        return annotations.size() > 0;
    }

    /**
     * Returns the method name. This is generally the getter method name for some field in the data model class.
     * @return the string representation of the name.
     */
    @NonNull
    String getMethodGetterName() {
        return getterName;
    }

    /**
     * A quick lookup to see if the method was annotated with a particular annotation.
     * @param cls the annoation {@link Class}.
     * @return true if this method was annotated with the input annotation, false otherwise.
     */
    boolean hasAnnotation(@NonNull Class<? extends Annotation> cls) {
        return annotations.containsKey(cls);
    }

    /**
     * Returns the actual annotation. Null if the method doesn't have that particular annotation.
     * @param cls the annotation {@link Class}.
     * @param <A> the generic must extend {@link Annotation}.
     * @return the annotation or null if the method wasn't annotated with it.
     */
    @Nullable
    <A extends Annotation> A getAnnotation(@NonNull Class<A> cls) {
        return (A) annotations.get(cls);
    }
}
