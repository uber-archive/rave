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

package com.ubercab.rave;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that holds the {@link ValidationIgnore} rules for Rave validation calls.
 */
public final class ExclusionStrategy {

    @NonNull private final Map<Class<?>, ValidationIgnore> ignoreMap;

    private ExclusionStrategy(@NonNull Map<Class<?>, ValidationIgnore> ignoreMap) {
        this.ignoreMap = ignoreMap;
    }

    @Nullable
    ValidationIgnore get(Class<?> clazz) {
        return ignoreMap.get(clazz);
    }

    /**
     * The builder for the {@link ExclusionStrategy}.
     */
    public static class Builder {

        @NonNull private final Map<String, Class<?>> classMap = new HashMap<>();

        @Nullable private List<String> errors;
        @Nullable private Map<Class<?>, ValidationIgnore> ignoreMap;

        /**
         * Add an ignore rule for the entire class.
         *
         * @param className the canonical name of the {@link Class} to ignore.
         * @return the {@link ExclusionStrategy}
         */
        @NonNull
        public Builder addClass(@NonNull String className) {
            Class<?> clazz = getClass(className);
            if (clazz == null) {
                addError(className);
                return this;
            }
            return addClass(clazz);
        }

        /**
         * Add an ignore rule for the entire class.
         *
         * @param clazz the {@link Class} to ignore.
         * @return the {@link ExclusionStrategy}
         */
        @NonNull
        public Builder addClass(@NonNull Class<?> clazz) {
            if (ignoreMap == null) {
                ignoreMap = new HashMap<>();
            }
            ValidationIgnore ignore = new ValidationIgnore(clazz);
            ignore.setAsIgnoreClassAll();
            ignoreMap.put(clazz, ignore);
            return this;
        }

        /**
         * Add a rule to ignore a method within a certain class.
         *
         * @param className the canonical name of the class to apply the ignore method rule to..
         * @param methodName the name of the method to ignore.
         * @return the {@link ExclusionStrategy}
         */
        @NonNull
        public Builder addMethod(@NonNull String className, @NonNull String methodName) {
            Class<?> clazz = getClass(className);
            if (clazz == null) {
                addError(className);
                return this;
            }
            return addMethod(clazz, methodName);
        }

        /**
         * Add a rule to ignore a method within a certain class.
         *
         * @param clazz the {@link Class} to apply the ignore method rule to.
         * @param methodName the name of the method to ignore.
         * @return the {@link ExclusionStrategy}
         */
        @NonNull
        public Builder addMethod(@NonNull Class<?> clazz, @NonNull String methodName) {
            try {
                clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                addError(clazz.getCanonicalName() + ":" + methodName);
                return this;
            }
            if (ignoreMap == null) {
                ignoreMap = new HashMap<>();
            }
            ValidationIgnore ignore = ignoreMap.get(clazz);
            if (ignore == null) {
                ignore = new ValidationIgnore(clazz);
                ignoreMap.put(clazz, ignore);
            }
            ignore.addMethod(methodName);
            return this;
        }

        /**
         * @return true if there were/are any errors during or after the building the {@link Map}.
         */
        public boolean hasErrors() {
            return errors != null;
        }

        /**
         * @return a list of {@link String}s representing the classnames/methods that failed.
         */
        @Nullable
        public List<String> getErrors() {
            return errors;
        }

        /**
         * @return the {@link ExclusionStrategy}. This can be directly passed to the {@link Rave}
         * validation call. Will return an empty {@link ExclusionStrategy} if no ignore rules where specified in the
         * building process.
         */
        @NonNull
        public ExclusionStrategy build() {
            return (ignoreMap == null) ? new ExclusionStrategy(Collections.<Class<?>, ValidationIgnore>emptyMap())
                    : new ExclusionStrategy(ignoreMap);
        }

        private void addError(@NonNull String error) {
            if (errors == null) {
                errors = new ArrayList<>();
            }
            errors.add(error);
        }

        /**
         * Returns the {@link Class} from the class name. If the class doesn't exist than an error is added to this list
         * of errors.
         *
         * @param className the canonical class name.
         * @return the corresponding {@link Class}
         */
        @Nullable
        private Class<?> getClass(@NonNull String className) {
            Class<?> clazz;
            if (classMap.containsKey(className)) {
                return classMap.get(className);
            } else {
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    return null;
                }
                classMap.put(className, clazz);
            }
            return clazz;
        }
    }
}
