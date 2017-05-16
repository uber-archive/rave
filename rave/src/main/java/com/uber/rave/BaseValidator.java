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

package com.uber.rave;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * This class should only be used by generated validator classes.
 */
public abstract class BaseValidator {

    private final HashSet<Class<?>> supportedClasses;

    protected BaseValidator() {
        supportedClasses = new HashSet<>();
    }

    final void validate(Object object) throws RaveException {
        Class<?> clazz = object.getClass();
        if (supportedClasses.contains(clazz)) {
            // This will call the object specific generated validate method.
            validateAs(object, clazz);
        } else {
            throw new IllegalArgumentException(
                    clazz.getCanonicalName() + ":" + RaveErrorStrings.CLASS_NOT_SUPPORTED_ERROR);
        }
    }

    protected final Set<Class<?>> getSupportedClasses() {
        return supportedClasses;
    }

    /**
     * This method targets the inheritance issue for validation. Objects can have multiple validation classes that are
     * inherited. It allows you to specifically validate an object as an inherited class or interface.
     *
     * @param obj the object to validate
     * @param clazz the class to validate it as.
     * @throws RaveException if validation fails.
     */
    protected abstract void validateAs(
            Object obj,
            Class<?> clazz)
            throws RaveException;

    /**
     * This class kicks the validate call back up through the RAVE api. This is because this particular validator may
     * not know how to handle the input class. Using the Rave.validate api allows Rave to correctly route the object
     * to the appropriate validator.
     *
     * @param clazz the class to validate this object as.
     * @param obj the object to validate
     * @return A list of {@link RaveError}s.
     */
    @Nullable
    protected final List<RaveError> reEvaluateAsSuperType(
            Class<?> clazz,
            Object obj) {
        try {
            Rave.getInstance().validateAs(obj, clazz);
        } catch (RaveException e) {
            return e.errors;
        }
        return null;
    }

    /**
     * Register this {@link BaseValidator} with the {@link Rave} registry.
     */
    protected final void registerSelf() {
        Rave.getInstance().registerValidator(this, supportedClasses);
    }

    /**
     * Add a supported class to this {@link BaseValidator}.
     *
     * @param clazz the {@link Class} to add.
     */
    protected final void addSupportedClass(Class<?> clazz) {
        supportedClasses.add(clazz);
    }

    /**
     * Return an instance of a {@link BaseValidator.ValidationContext}.
     *
     * @param clazz the {@link Class} to tie to the {@link BaseValidator.ValidationContext}
     * @return the {@link BaseValidator.ValidationContext}
     */
    protected static ValidationContext getValidationContext(Class<?> clazz) {
        return new ValidationContext(clazz);
    }

    @Nullable
    protected static List<RaveError> mustBeFalse(boolean input, ValidationContext validationContext) {
        if (input) {
            List<RaveError> list = new LinkedList<>();
            list.add(new RaveError(validationContext, RaveErrorStrings.MUST_BE_FALSE_ERROR));
            return list;
        }
        return null;
    }

    /**
     * Checks to see if the size of the input string is of a size as denoted by {@code min} and {@code max}. Also check
     * the {@code multiple} constraint of size.
     *
     * @param string The string to validate.
     * @param isNullable if true than null is a valid value for the input string regardless of min and max.
     * @param min The string must be at least this long.
     * @param max The string must not exceed this length.
     * @param multiple The multiple constraint on the {@link android.support.annotation.Size}. If less than zero it is
     * ignored.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @return {@link List} of {@link RaveError}s which list the validation violations. Null otherwise.
     */
    protected static List<RaveError> isSizeOk(
            @Nullable String string, boolean isNullable, long min, long max, long multiple,
            ValidationContext validationContext) {
        List<RaveError> errors = checkNullable(string, isNullable, validationContext);
        if (string == null) {
            return errors;
        }
        int stringSize = string.length();
        errors = testMultipleParameter(multiple, stringSize, validationContext, "String", errors);
        if (stringSize <= max && stringSize >= min) {
            return errors;
        }
        String msg = "String size out of bounds. Size is: " + string.length() + " when should be "
                + "between " + min + " and " + max;
        return appendError(validationContext, msg, errors);
    }

    /**
     * Validates a collection.
     *
     * @param collection the collection to validate.
     * @param isNullable if true than null is a valid value for the input string regardless of min and max.
     * @param min The collection must have least this many elements in it.
     * @param max The collection can have at most this many elements in it.
     * @param multiple The multiple constraint on the {@link android.support.annotation.Size}. If less than zero it is
     * ignored.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @return {@link List} of {@link RaveError}s which list the validation violations. Null otherwise.
     */
    protected static List<RaveError> isSizeOk(
            @Nullable Collection<?> collection,
            boolean isNullable,
            long min,
            long max,
            long multiple,
            ValidationContext validationContext) {
        List<RaveError> raveErrors = checkNullable(collection, isNullable, validationContext);
        if (collection == null) {
            return raveErrors;
        }
        int collectionSize = collection.size();
        raveErrors = testMultipleParameter(multiple, collectionSize, validationContext,
                collection.getClass().getCanonicalName(), raveErrors);
        if (collectionSize <= max && collectionSize >= min) {
            return raveErrors;
        }
        String msg = collection.getClass().getCanonicalName() + " is not within bounds min:" + min
                + " max:" + max;
        return appendError(validationContext, msg, raveErrors);
    }

    /**
     * Validate the size of an Array.
     *
     * @param array the array to validate.
     * @param isNullable if true than null is a valid value for the input string regardless of min and max.
     * @param min The Array must have least this many elements in it.
     * @param max The Array can have at most this many elements in it.
     * @param multiple The multiple constraint on the {@link android.support.annotation.Size}. If less than zero it is
     * ignored.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @param <T> can be anytype.
     * @return {@link List} of {@link RaveError}s which list the validation violations. Null otherwise.
     */
    @Nullable
    protected static <T> List<RaveError> isSizeOk(
            @Nullable T[] array, boolean isNullable, long min, long max, long multiple,
            ValidationContext validationContext) {
        List<RaveError> raveErrors = checkNullable(array, isNullable, validationContext);
        if (array == null) {
            return raveErrors;
        }
        raveErrors = testMultipleParameter(multiple, array.length, validationContext, "", raveErrors);
        if (array.length <= max && array.length >= min) {
            return raveErrors;
        }
        String msg = "With size" + array.length + " is not within " + "bounds min:" + min + " and max:" + max;
        return appendError(validationContext, msg, raveErrors);
    }

    /**
     * Validate the size of an Map.
     *
     * @param map the {@link Map} to validate.
     * @param isNullable if true than null is a valid value for the input string regardless of min and max.
     * @param min the Array must have least this many elements in it.
     * @param max the Array can have at most this many elements in it.
     * @param multiple the multiple constraint on the {@link android.support.annotation.Size}. If less than zero it is
     * ignored.
     * @param validationContext the context of the item in the class being validated. This is used in case of an error.
     * @param <K> key type of the map and can be any type.
     * @param <V> value type of the map and can be any type.
     * @return {@link List} of {@link RaveError}s which list the validation violations. Null otherwise.
     */
    @Nullable
    protected static <K, V> List<RaveError> isSizeOk(
            @Nullable Map<K, V> map,
            boolean isNullable,
            long min,
            long max,
            long multiple,
            ValidationContext validationContext) {
        List<RaveError> raveErrors = checkNullable(map, isNullable, validationContext);
        if (map == null) {
            return raveErrors;
        }
        raveErrors = testMultipleParameter(multiple, map.size(), validationContext, "", raveErrors);
        if (map.size() <= max && map.size() >= min) {
            return raveErrors;
        }
        String msg = "With size " + map.size() + " is not within " + "bounds min: " + min + " and max: " + max;
        return appendError(validationContext, msg, raveErrors);
    }

    /**
     * Checks to see if the object is null.
     *
     * @param obj the object to validate.
     * @param isNullable is the object is allowed to be null.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @return a list of of {@link RaveError}s if the object is not allowed to be null. Returns null otherwise.
     */
    protected static List<RaveError> checkNullable(
            @Nullable Object obj,
            boolean isNullable,
            ValidationContext validationContext) {
        if (obj == null) {
            if (isNullable) {
                return Collections.<RaveError>emptyList();
            }
            List<RaveError> errors = new LinkedList<>();
            errors.add(new RaveError(validationContext.clazz, validationContext.validatedItemName,
                    RaveErrorStrings.NON_NULL_ERROR));
            return errors;
        }
        if (!(obj instanceof String)) {
            try {
                Rave.getInstance().validate(obj);
            } catch (UnsupportedObjectException e) {
                return Collections.<RaveError>emptyList();
            } catch (RaveException e) {
                return appendErrors(e, null);
            }
        }
        return Collections.<RaveError>emptyList();
    }

    /**
     * Checks to see if the object is null.
     *
     * @param collection the collection to validate.
     * @param isNullable is the object is allowed to be null.
     * @param validationContext the context of the item in the class being validated. This is used in case of an error.
     * @return a list of of {@link RaveError}s if the object is not allowed to be null. Returns null otherwise.
     */
    protected static List<RaveError> checkNullable(
            @Nullable Collection<?> collection,
            boolean isNullable,
            ValidationContext validationContext) {
        List<RaveError> errors = checkNullable((Object) collection, isNullable, validationContext);
        return collection == null ? errors : checkIterable(collection, errors);
    }

    /**
     * Checks to see if the object is null.
     *
     * @param array the array to validate.
     * @param isNullable is the object is allowed to be null.
     * @param validationContext the context of the item in the class being validated. This is used in case of an error.
     * @return a list of of {@link RaveError}s if the object is not allowed to be null. Returns null otherwise.
     */
    protected static <T> List<RaveError> checkNullable(
            @Nullable T[] array,
            boolean isNullable,
            ValidationContext validationContext) {
        List<RaveError> errors = checkNullable((Object) array, isNullable, validationContext);
        if (array == null) {
            return errors;
        }
        Rave rave = Rave.getInstance();
        for (T type : array) {
            if (type == null) {
                continue;
            }
            try {
                rave.validate(type);
            } catch (UnsupportedObjectException e) {
                return errors;
            } catch (RaveException e) {
                errors = appendErrors(e, errors);
            }
        }
        return errors;
    }

    /**
     * Checks to see if the map is null.
     *
     * @param map the map to validate.
     * @param isNullable is the object is allowed to be null.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @return a list of of {@link RaveError}s if the object is not allowed to be null. Returns null otherwise. This
     * method will also RAVE validate the keys and values of the map if they are RAVE enabled.
     */
    protected static <K, V> List<RaveError> checkNullable(
            @Nullable Map<K, V> map,
            boolean isNullable,
            ValidationContext validationContext) {
        List<RaveError> errors = checkNullable((Object) map, isNullable, validationContext);
        if (map == null) {
            return errors;
        }
        errors = checkIterable(map.keySet(), errors);
        return checkIterable(map.values(), errors);
    }

    /**
     * Takes two RaveError lists and merges them into one.
     *
     * @param e1 the first list of {@link RaveError}s
     * @param e2 the second list of {@link RaveError}s
     * @return the combination of the two or null if both were null.
     */
    @Nullable
    protected static List<RaveError> mergeErrors(@Nullable List<RaveError> e1, @Nullable List<RaveError> e2) {
        if (e1 == null || e1.isEmpty()) {
            return e2;
        }
        if (e2 == null || e2.isEmpty()) {
            return e1;
        }
        e1.addAll(e2);
        return e1;
    }

    /**
     * Verifies the input to be always true.
     *
     * @param input the value to validate.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @return null if input is true and a {@link List} otherwise.
     */
    protected static List<RaveError> mustBeTrue(boolean input, ValidationContext validationContext) {
        return input ? Collections.<RaveError>emptyList()
                : createNewList(new RaveError(validationContext, RaveErrorStrings.MUST_BE_TRUE_ERROR));
    }

    /**
     * Utility to check if a string is present in a list of strings. If the string is null then do nothing.
     *
     * @param isNullable if the value is allowed to be null or not.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @param value string to look for.
     * @param acceptableValues list of strings to look in.
     * @return returns an error if string is not present in list. Otherwise returns an empty list.
     */
    protected static List<RaveError> checkStringDef(
            boolean isNullable, ValidationContext validationContext, @Nullable String value,
            String... acceptableValues) {
        List<RaveError> errors = checkNullable(value, isNullable, validationContext);
        if (value == null) {
            return errors;
        }

        for (String string : acceptableValues) {
            if (value.equals(string)) {
                return Collections.<RaveError>emptyList();
            }
        }
        return createStringDefError(value, acceptableValues, validationContext);
    }

    /**
     * Utility to check if the strings in a {@link Collection} of strings match acceptable values. If the input
     * {@link Collection} is null then nothing is done.
     *
     * @param isNullable if the value is allowed to be null or not.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @param values a {@link Collection} of strings to validate.
     * @param acceptableValues list acceptable values for the input list.
     * @return returns an error if string is not present in list. Otherwise returns an empty list.
     */
    protected static List<RaveError> checkStringDef(
            boolean isNullable,
            ValidationContext validationContext,
            @Nullable Collection<String> values,
            String... acceptableValues) {
        List<RaveError> errors = checkNullable(values, isNullable, validationContext);
        if (values == null) {
            return errors;
        }
        for (String s : values) {
            errors = mergeErrors(errors, checkStringDef(false, validationContext, s, acceptableValues));
        }
        return errors == null ? Collections.<RaveError>emptyList() : errors;
    }

    /**
     * Utility to check if the strings in a array of strings match acceptable values. If the input array is null then
     * nothing is done.
     *
     * @param isNullable if the value is allowed to be null or not.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @param values a array of strings to validate.
     * @param acceptableValues list acceptable values for the input array.
     * @return returns an error if string is not present in list. Otherwise returns an empty list.
     */
    protected static List<RaveError> checkStringDef(
            boolean isNullable,
            ValidationContext validationContext,
            @Nullable String[] values,
            String... acceptableValues) {
        List<RaveError> errors = checkNullable(values, isNullable, validationContext);
        if (values == null) {
            return errors;
        }
        for (String s : values) {
            errors = mergeErrors(errors, checkStringDef(false, validationContext, s, acceptableValues));
        }
        return errors == null ? Collections.<RaveError>emptyList() : errors;
    }

    /**
     * Check a double value to be in the correct range. {@code from} and {@code to} are used as the boundry checks and
     * are inclusive. Note {@link Double#POSITIVE_INFINITY} or {@link Double#NEGATIVE_INFINITY} are supported.
     * If used any value is always greater than {@link Double#NEGATIVE_INFINITY} and always less than
     * {@link Double#POSITIVE_INFINITY} by definition.
     *
     * @param validationContext the {@link BaseValidator.ValidationContext}
     * @param value the value to check
     * @param from the lower bound of the check (inclusive)
     * @param to the upper bound of the check (inclusive)
     * @return the {@link RaveError} if the checked value is not within the bounds.
     */
    protected static List<RaveError> checkFloatRange(
            ValidationContext validationContext,
            double value,
            double from,
            double to) {
        boolean lowerIsOk = (from == Double.NEGATIVE_INFINITY) || value >= from;
        boolean upperIsOk = (to == Double.POSITIVE_INFINITY) || value <= to;
        if (lowerIsOk && upperIsOk) {
            return Collections.<RaveError>emptyList();
        }
        return createNewList(new RaveError(validationContext, value + " " + RaveErrorStrings.FLOAT_RANGE_ERROR
                + " which should be between " + from + " and " + to));
    }

    /**
     * Checks the input long value is within the bounds of {@code from} and {@code to} inclusive.
     *
     * @param validationContext the {@link BaseValidator.ValidationContext}
     * @param value the value to check.
     * @param from the lower bound of the check, inclusive.
     * @param to the upper bound of the check, inclusive.
     * @return the {@link RaveError} if the checked value is not within the bounds.
     */
    protected static List<RaveError> checkIntRange(
            ValidationContext validationContext,
            long value,
            long from,
            long to) {
        if (value <= to && value >= from) {
            return Collections.<RaveError>emptyList();
        }
        return createNewList(new RaveError(validationContext, value + " " + RaveErrorStrings.INT_RANGE_ERROR + " which "
                + "should be between " + from + " and " + to));
    }

    /**
     * Check the value of an input long to see if it matches the acceptable values. This is the check that is run for
     * the {@link android.support.annotation.IntDef} annotation. Note the flag value for this is currently unused but
     * we may add support later.
     *
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @param value The long value to check.
     * @param flag the is the flag value from {@link android.support.annotation.IntDef} it is currently unsupported.
     * @param acceptableValues the values that the value parameter can take on.
     * @return an error if value is not present in list of acceptable values. Otherwise returns an empty list.
     */
    protected static List<RaveError> checkIntDef(
            ValidationContext validationContext, long value, boolean flag,
            long... acceptableValues) {

        for (long acceptable : acceptableValues) {
            if (value == acceptable) {
                return Collections.<RaveError>emptyList();
            }
        }
        return createIntDefError(value, acceptableValues, validationContext);
    }

    /**
     * Validate the elements in a {@link Iterable}.
     *
     * @param iterable the items to check
     * @param errors the {@link List} of errros seen so far.
     * @param <T> The type of each object in the {@link Iterable}.
     * @return any new errors.
     */
    private static <T> List<RaveError> checkIterable(Iterable<T> iterable, @Nullable List<RaveError> errors) {
        Rave rave = Rave.getInstance();
        for (T type : iterable) {
            if (type == null) {
                continue;
            }
            try {
                rave.validate(type);
            } catch (UnsupportedObjectException e) {
                return (errors == null) ? Collections.<RaveError>emptyList() : errors;
            } catch (RaveException e) {
                errors = appendErrors(e, errors);
            }
        }
        return (errors == null) ? Collections.<RaveError>emptyList() : errors;
    }

    /**
     * Append a {@link List} of errors retrieved from a {@link RaveException} to the current {@link List} of errors.
     *
     * @param e the {@link RaveException} to get the errors from.
     * @param errors the {@link List} to add to.
     * @return a new {@link List} if the input {@link List} was null or just append to the input {@link List}.
     */
    private static List<RaveError> appendErrors(@Nullable RaveException e, @Nullable List<RaveError> errors) {
        if (errors == null) {
            errors = new LinkedList<>();
        }
        if (e == null) {
            return errors;
        }
        try {
            errors.addAll(e.errors);
        } catch (UnsupportedOperationException e1) {
            List<RaveError> errorList = new LinkedList<>();
            errorList.addAll(e.errors);
            errorList.addAll(errors);
            return errorList;
        }
        return errors;
    }

    /**
     * Merge a msg with a {@link List}s of {@link RaveError}s.
     *
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @param msg the message to add to the list.
     * @param raveErrors the {@link List} of {@link RaveError} to add to.
     * @return a new {@link List} or {@link RaveError}s
     */
    private static List<RaveError> appendError(
            ValidationContext validationContext,
            String msg,
            @Nullable List<RaveError> raveErrors) {
        // If the list is empty it is also immutable so we need a new one.
        if (raveErrors == null || raveErrors.isEmpty()) {
            return createNewList(new RaveError(validationContext, msg));
        }
        raveErrors.add(new RaveError(validationContext, msg));
        return raveErrors;
    }

    /**
     * Simple utility function to create a new list.
     *
     * @param error the {@link RaveError} to add to the list.
     * @return a newly  populated {@link List}.
     */
    private static List<RaveError> createNewList(RaveError error) {
        List<RaveError> errors = new LinkedList<>();
        errors.add(error);
        return errors;
    }

    /**
     * Checks to see if the size parameter of an some element is a multiple of the in input multiple value.
     *
     * @param multiple the multiple value to check the size against.
     * @param size the size value to check against.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @param elementType the type of the element being checked.
     * @param raveErrors the list of {@link RaveError}s.
     * @return a list of {@link RaveError}.
     */
    private static List<RaveError> testMultipleParameter(
            long multiple,
            int size,
            ValidationContext validationContext,
            String elementType,
            List<RaveError> raveErrors) {
        if (multiple >= 0 && size % multiple != 0) {
            String msg = elementType + " is not a multiple of " + multiple + ", size is " + size;
            raveErrors = appendError(validationContext, msg, raveErrors);
        }
        return raveErrors;
    }

    /**
     * Create the error for stringdef.
     *
     * @param value the string value
     * @param acceptableValues the acceptable values for the string.
     * @param validationContext The context of the item in the class being validated. This is used in case of an error.
     * @return a {@link RaveError} corresponding to the non matching error.
     */
    private static List<RaveError> createStringDefError(
            @Nullable String value,
            String[] acceptableValues,
            ValidationContext validationContext) {

        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (String string : acceptableValues) {
            if (!first) {
                stringBuilder.append(" ");
            } else {
                stringBuilder.append("{");
            }
            stringBuilder.append(string);
            first = false;
        }
        stringBuilder.append("}");

        return createNewList(
                new RaveError(validationContext, value + " " + RaveErrorStrings.STRING_DEF_ERROR
                        + stringBuilder.toString()));
    }

    private static List<RaveError> createIntDefError(
            long value,
            long[] acceptableValues,
            ValidationContext validationContext) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (long string : acceptableValues) {
            if (!first) {
                stringBuilder.append(" ");
            } else {
                stringBuilder.append("{");
            }
            stringBuilder.append(string);
            first = false;
        }
        stringBuilder.append("}");

        return createNewList(
                new RaveError(validationContext, value + " " + RaveErrorStrings.INT_DEF_ERROR
                        + stringBuilder.toString()));
    }

    /**
     * This class encapsulates the validation context from which a validation check is happening. This class is
     * primarily used to for retrieving the point of failure for an error.
     */
    public static final class ValidationContext {

        private final Class<?> clazz;
        private String validatedItemName = "";

        private ValidationContext(Class<?> clazz) {
            this.clazz = clazz;
        }

        Class<?> getClazz() {
            return clazz;
        }

        String getValidatedItemName() {
            return validatedItemName;
        }

        /**
         * The the context name for this {@link ValidationContext} object.
         * @param item the string name for the current context.
         */
        public void setValidatedItemName(String item) {
            this.validatedItemName = item;
        }
    }
}
