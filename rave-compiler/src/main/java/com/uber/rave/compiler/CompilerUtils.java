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
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.StringDef;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.uber.rave.annotation.MustBeFalse;
import com.uber.rave.annotation.MustBeTrue;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * Utilities class for annotations.
 */
final class CompilerUtils {

    private static final List<Class<? extends Annotation>> SUPPORT_ANNOTATIONS = ImmutableList.of(
            NonNull.class,
            Nullable.class,
            Size.class,
            MustBeFalse.class,
            MustBeTrue.class,
            StringDef.class,
            IntDef.class,
            LongDef.class,
            IntRange.class,
            FloatRange.class
    );

    private static final List<Pair<Class<? extends Annotation>, Class<? extends Annotation>>>
        CONFLICTING_SUPPORT_ANNOTATIONS = ImmutableList.of(
                    new Pair<Class<? extends Annotation>, Class<? extends Annotation>>(MustBeTrue.class,
                            MustBeFalse.class),
                    new Pair<Class<? extends Annotation>, Class<? extends Annotation>>(IntRange.class, IntDef.class),
                    new Pair<Class<? extends Annotation>, Class<? extends Annotation>>(IntRange.class, LongDef.class));
    private static Map<Class<? extends Annotation>, Set<Class<? extends Annotation>>> sConflictingAnnotations;
    private static Map<String, Class<? extends Annotation>> sAnnotationMap;

    static {
        init();
    }

    private CompilerUtils() { }

    private static void build(
            Map<Class<? extends Annotation>, ImmutableSet.Builder<Class<? extends Annotation>>> temp,
            Class<? extends Annotation> annotation1, Class<? extends Annotation> annotation2) {
        ImmutableSet.Builder<Class<? extends Annotation>> builder = temp.get(annotation1);
        if (builder == null) {
            builder = new ImmutableSet.Builder<>();
            temp.put(annotation1, builder);
        }
        builder.add(annotation2);
    }

    private static void init() {
        ImmutableMap.Builder<String, Class<? extends Annotation>> annotationBuilder = ImmutableMap.builder();
        for (Class<? extends Annotation> cls : SUPPORT_ANNOTATIONS) {
            annotationBuilder.put(cls.getCanonicalName(), cls);
        }
        sAnnotationMap = annotationBuilder.build();

        Map<Class<? extends Annotation>, ImmutableSet.Builder<Class<? extends Annotation>>> mapBuilder =
                new HashMap<>();
        for (Pair<Class<? extends Annotation>, Class<? extends Annotation>> pair : CONFLICTING_SUPPORT_ANNOTATIONS) {
            build(mapBuilder, pair.first, pair.second);
            build(mapBuilder, pair.second, pair.first);
        }

        ImmutableMap.Builder<Class<? extends Annotation>, Set<Class<? extends Annotation>>> builder = ImmutableMap
                .builder();
        for (Class<? extends Annotation> annotation : SUPPORT_ANNOTATIONS) {
            ImmutableSet.Builder<Class<? extends Annotation>> conflicting = mapBuilder.get(annotation);
            if (conflicting == null) {
                conflicting = new ImmutableSet.Builder<>();
            }
            builder.put(annotation, conflicting.build());
        }

        sConflictingAnnotations = builder.build();
    }

    /**
     * Checks to see if a given {@link TypeMirror} is in the given {@link Collection}.
     *
     * @param typeCollection the {@link Collection} to check.
     * @param typeMirror the {@link TypeMirror} to check for in the given {@link Collection}.
     * @param typesUtils {@link Types} utility class that helps with the check.
     * @return true if the {@link TypeMirror} is found in the {@link Collection}.
     */
    static boolean typeMirrorInCollection(
            Collection<TypeMirror> typeCollection,
            TypeMirror typeMirror,
            Types typesUtils) {
        for (TypeMirror mirror : typeCollection) {
            if (typesUtils.isSameType(mirror, typeMirror)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param annotationName the annotation, by fully qualified name, to check.
     * @return true if the annotation is an Android support annotation, false otherwise.
     */
    static boolean isSupportAnnotation(String annotationName) {
        return sAnnotationMap.containsKey(annotationName);
    }

    /**
     * @param annotationName the annotation, by fully qualified name, to check.
     * @return true if the annotation is an supported by RAVE, false otherwise.
     */
    static boolean isSupportedAnnotation(String annotationName) {
        return annotationName.toLowerCase().contains("nullable")
            || annotationName.toLowerCase().contains("nonnull")
            || isSupportAnnotation(annotationName);
    }

    /**
     * @param annotationName the annotation (canonical name) to retrieve.
     * @return the {@link Class} of the Android support annotation.
     */
    static Class<? extends Annotation> getSupportAnnotation(String annotationName) {
        return sAnnotationMap.get(annotationName);
    }

    /**
     * This method checks to see if two annotations are conflicting or not.
     *
     * @param annotation1 the first annotation, by fully qualified name to check.
     * @param annotation2 the second annotation by fully qualified name to check.
     * @return true if the annotations are conflicting, false otherwise.
     */
    static boolean areConflicting(String annotation1, String annotation2) {
        String a1 = annotation1.toLowerCase();
        String a2 = annotation2.toLowerCase();
        if ((a1.contains("nullable") && a2.contains("nonnull"))
                || (a1.contains("nonnull") && a2.contains("nullable"))) {
            return true;
        }
        Set<Class<? extends Annotation>> set = sConflictingAnnotations.get(getSupportAnnotation(annotation1));
        return set.contains(getSupportAnnotation(annotation2));
    }

    /**
     * Returns the name of the package that the given type is in. If the type is in the default
     * (unnamed) package then the name is the empty string.
     */
    static String packageNameOf(Element type) {
        while (true) {
            Element enclosing = type.getEnclosingElement();
            if (enclosing instanceof PackageElement) {
                return ((PackageElement) enclosing).getQualifiedName().toString();
            }
            type = enclosing;
        }
    }

    /**
     * Represents a conflicting pair of annotations.
     * @param <A> Item 1
     * @param <B> Item 2
     */
    private static final class Pair<A, B> {

        final A first;
        final B second;

        Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }
    }
}
