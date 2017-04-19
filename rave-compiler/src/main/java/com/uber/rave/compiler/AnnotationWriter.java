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

import com.squareup.javapoet.MethodSpec;
import com.uber.rave.BaseValidator;
import com.uber.rave.Rave;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.TypeMirror;

/**
 * A helper class that understands how to write out the various supported {@link Rave} annotations.
 */
final class AnnotationWriter {

    // Method names
    static final String CHECK_SIZE_METHOD_NAME = "isSizeOk";
    static final String CHECK_NULL_METHOD_NAME = "checkNullable";
    static final String CHECK_MUST_BE_TRUE_METHOD_NAME = "mustBeTrue";
    static final String CHECK_MUST_BE_FALSE_METHOD_NAME = "mustBeFalse";
    static final String CHECK_MUST_BE_STRING_DEF_VALUE = "checkStringDef";
    static final String CHECK_MUST_BE_INT_DEF_VALUE = "checkIntDef";
    static final String CHECK_INT_RANGE_METHOD_NAME = "checkIntRange";
    static final String CHECK_FLOAT_RANGE_METHOD_NAME = "checkFloatRange";
    static final String MERGE_ERROR_METHOD_NAME = "mergeErrors";
    static final String SHOULD_IGNORE_METHOD_NAME = "setContextAndCheckshouldIgnoreMethod";

    // JavaPoet formats
    private static final String LITERAL = "$L";
    private static final String LITERAL_DOUBLE = "$LD";
    private static final String LITERAL_LONG = "$LL";
    private static final String NAMES = "$N";
    private static final String STRING_LITERAL = "$S";

    @NonNull private final TypeMirror typeMirror;
    @NonNull private final MethodSpec.Builder builder;
    @NonNull private final MethodSpec getter;
    private final boolean isNullable;
    private final boolean hasNonNullOrNullable;

    /**
     * @param typeMirror the {@link TypeMirror} of the class being evaluated.
     * @param builder the {@link MethodSpec.Builder} that is used to generate the code.
     * @param getter the {@link MethodSpec} for the getter method being evaluated.
     * @param isNullable true if the method DOES NOT have have a {@link NonNull} annotation.
     * @param hasNonNullOrNullable true if either {@link NonNull} or {@link Nullable} annotations are present on the
     * method.
     */
    AnnotationWriter(
            @NonNull TypeMirror typeMirror,
            @NonNull MethodSpec.Builder builder,
            @NonNull MethodSpec getter,
            boolean isNullable,
            boolean hasNonNullOrNullable) {
        this.typeMirror = typeMirror;
        this.builder = builder;
        this.getter = getter;
        this.isNullable = isNullable;
        this.hasNonNullOrNullable = hasNonNullOrNullable;
    }

    void writeNullable() {
        // Args: value, isNullable, validationContext
        BaseAnnotationWriter baseWriter = new BaseAnnotationWriter(getter, CHECK_NULL_METHOD_NAME, false);
        baseWriter.addArg(LITERAL, isNullable, true);
        baseWriter.addArg(LITERAL, RaveWriter.VALIDATION_CONTEXT_ARG_NAME, true);
        buildStatements(baseWriter.getFormattedString(), baseWriter.getArgs());
    }

    void writeMustBeFalse() {
        // Args: value, validationContext
        BaseAnnotationWriter baseWriter = new BaseAnnotationWriter(getter, CHECK_MUST_BE_FALSE_METHOD_NAME, false);
        baseWriter.addArg(LITERAL, RaveWriter.VALIDATION_CONTEXT_ARG_NAME, true);
        buildStatements(baseWriter.getFormattedString(), baseWriter.getArgs());
    }

    void writeMustBeTrue() {
        // Args: value, validationContext
        BaseAnnotationWriter baseWriter = new BaseAnnotationWriter(getter, CHECK_MUST_BE_TRUE_METHOD_NAME, false);
        baseWriter.addArg(LITERAL, RaveWriter.VALIDATION_CONTEXT_ARG_NAME, true);
        buildStatements(baseWriter.getFormattedString(), baseWriter.getArgs());
    }

    void write(@Nullable Size size) {
        checkAnnotationNotNull(size);
        long min;
        long max;
        if (size.value() >= 0) {
            min = size.value();
            max = min;
        } else {
            min = size.min();
            max = size.max();
        }
        // Args: value, isNullable, min, max, multiple, validationContext
        BaseAnnotationWriter baseWriter = new BaseAnnotationWriter(getter, CHECK_SIZE_METHOD_NAME, false);
        baseWriter.addArg(LITERAL, isNullable, true);
        baseWriter.addArg(LITERAL_LONG, min, true);
        baseWriter.addArg(LITERAL_LONG, max, true);
        baseWriter.addArg(LITERAL_LONG, size.multiple(), true);
        baseWriter.addArg(LITERAL, RaveWriter.VALIDATION_CONTEXT_ARG_NAME, true);
        buildStatements(baseWriter.getFormattedString(), baseWriter.getArgs());
    }

    void write(@Nullable StringDef stringDef) {
        checkAnnotationNotNull(stringDef);
        boolean isStringDefNullable = hasNonNullOrNullable && isNullable;
        String[] acceptableStrings = stringDef.value();
        //Args: isNullable, validationContext, value(s), acceptableValues
        BaseAnnotationWriter baseWriter = new BaseAnnotationWriter(CHECK_MUST_BE_STRING_DEF_VALUE);
        baseWriter.addArg(LITERAL, isStringDefNullable, false);
        baseWriter.addArg(LITERAL, RaveWriter.VALIDATION_CONTEXT_ARG_NAME, true);
        baseWriter.addGetterCall(getter, LITERAL);
        for (String string : acceptableStrings) {
            baseWriter.addArg(STRING_LITERAL, string, true);
        }
        buildStatements(baseWriter.getFormattedString(), baseWriter.getArgs());
    }

    void write(@Nullable IntDef intDef) {
        checkAnnotationNotNull(intDef);
        // Args: validationContext, value, flag, acceptableValues
        BaseAnnotationWriter baseWriter = new BaseAnnotationWriter(getter, CHECK_MUST_BE_INT_DEF_VALUE, true);
        baseWriter.addArg(LITERAL, intDef.flag(), true);
        long[] acceptableInts = intDef.value();
        for (long intVale : acceptableInts) {
            baseWriter.addArg(LITERAL_LONG, intVale, true);
        }
        buildStatements(baseWriter.getFormattedString(), baseWriter.getArgs());
    }

    void write(@Nullable IntRange intRange) {
        checkAnnotationNotNull(intRange);
        // Args: validationContext, value, from, to
        BaseAnnotationWriter baseWriter = new BaseAnnotationWriter(getter, CHECK_INT_RANGE_METHOD_NAME, true);
        baseWriter.addArg(LITERAL_LONG, intRange.from(), true);
        baseWriter.addArg(LITERAL_LONG, intRange.to(), true);
        buildStatements(baseWriter.getFormattedString(), baseWriter.getArgs());
    }

    void write(@Nullable FloatRange floatRange) {
        checkAnnotationNotNull(floatRange);
        // Args: validationContext, value, from, to
        BaseAnnotationWriter baseWriter = new BaseAnnotationWriter(getter, CHECK_FLOAT_RANGE_METHOD_NAME, true);
        if (Double.isInfinite(floatRange.from())) {
            baseWriter.addArg("Double.NEGATIVE_INFINITY", null, true);
        } else {
            baseWriter.addArg(LITERAL_DOUBLE, floatRange.from(), true);
        }
        if (Double.isInfinite(floatRange.to())) {
            baseWriter.addArg("Double.POSITIVE_INFINITY", null, true);
        } else {
            baseWriter.addArg(LITERAL_DOUBLE, floatRange.to(), true);
        }
        buildStatements(baseWriter.getFormattedString(), baseWriter.getArgs());
    }

    private void checkAnnotationNotNull(Annotation annotation) {
        if (annotation == null) {
            throw new RuntimeException("For method " + getter.name + " annotation is empty");
        }
    }

    /**
     * Adds the ignore if statement check to the method and then the check statement itself.
     */
    private void buildStatements(@NonNull String statementFormat, Object... objects) {
        builder.beginControlFlow("if (!$L($T.class, $S, $L, $L))", SHOULD_IGNORE_METHOD_NAME,
                typeMirror, getter.name, RaveWriter.EXCLUSION_STRATEGY_MAP_ARG_NAME,
                RaveWriter.VALIDATION_CONTEXT_ARG_NAME);
        builder.addStatement(statementFormat, objects);
        builder.endControlFlow();
    }

    /**
     * This class makes writing string format and output objects to JavaPoet a bit easier and more organized.
     *
     */
    private static final class BaseAnnotationWriter {

        @NonNull private final StringBuilder builder = new StringBuilder();
        @NonNull private final List<Object> args = new ArrayList<>();

        /**
         * Use this constructor if you want to auto generate the getter function call as one of the first params. If the
         * boolean parameter is set to true then the {@link BaseValidator.ValidationContext} object
         * is inserted first otherwise it is not.  This is a convenience method for the
         * {@link BaseValidator} methods that start with either the getter method or the context.
         * @param getter the {@link MethodSpec} for the getter.
         * @param checkMethodName the name of the checker method being called.
         * @param contextFirst if true this will insert the {@link BaseValidator.ValidationContext}
         * parameter as the first parameter otherwise it won't be inserted at all.
         */
        private BaseAnnotationWriter(
                @NonNull MethodSpec getter, @NonNull String checkMethodName,
                boolean contextFirst) {
            addArg(LITERAL + " = ", RaveWriter.RAVE_ERROR_ARG_NAME, false);
            addArg(LITERAL + "(", MERGE_ERROR_METHOD_NAME, false);
            addArg(LITERAL, RaveWriter.RAVE_ERROR_ARG_NAME, false);
            addArg(LITERAL + "(", checkMethodName, true);
            if (contextFirst) {
                addArg(LITERAL, RaveWriter.VALIDATION_CONTEXT_ARG_NAME, false);
            }
            addArg(LITERAL + ".", RaveWriter.VALIDATE_METHOD_ARG_NAME, contextFirst);
            addArg(NAMES + "()", getter, false);
        }

        /**
         * The basic constructor. This gives you the first few args for the checks.
         * @param checkMethodName the name of the checker method.
         */
        private BaseAnnotationWriter(@NonNull String checkMethodName) {
            addArg(LITERAL + " = ", RaveWriter.RAVE_ERROR_ARG_NAME, false);
            addArg(LITERAL + "(", MERGE_ERROR_METHOD_NAME, false);
            addArg(LITERAL, RaveWriter.RAVE_ERROR_ARG_NAME, false);
            addArg(LITERAL + "(", checkMethodName, true);
        }

        /**
         * Add the getter arg to the string format.
         * @param getter the {@link MethodSpec} of the getter.
         * @param format the format of the item returned from the getter.
         */
        private void addGetterCall(@NonNull MethodSpec getter, @NonNull String format) {
            addArg(format + ".", RaveWriter.VALIDATE_METHOD_ARG_NAME, true);
            addArg(NAMES + "()", getter, false);
        }

        /**
         * Add a string format and the corresponding object.
         * @param format the format string to append to the format string so far.
         * @param arg this can either be an object or a null item.
         * @param addSeparatorBefore add a comma separator BEFORE this argument.
         */
        void addArg(@NonNull String format, @Nullable Object arg, boolean addSeparatorBefore) {
            if (addSeparatorBefore) {
                builder.append(", ");
            }
            builder.append(format);
            if (arg != null) {
                args.add(arg);
            }
        }

        /**
         * @return This method returns the formatted string that is passed to javapoet to use.
         */
        String getFormattedString() {
            builder.append("))");
            return builder.toString();
        }

        /**
         * @return the list of objects that corresponds to the formated string.
         */
        Object[] getArgs() {
            return args.toArray(new Object[args.size()]);
        }
    }
}
