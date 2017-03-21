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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.uber.rave.annotation.Validated;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * The main entry point for Rave validation.
 * </p>
 * <pre>
 *     To use Rave in your library create add the following class to some package in your library.
 *     {@code
 *          public final class MyFactory implements ValidatorFactory {
 *             public BaseValidator generateValidator() {
 *                   return new MyFactory_Generated_Validator();
 *             }
 *          }
 *     }
 *     You can replace MyFactory with an class name like. However we recommend [whatever]Factory as a convention.
 * </pre>
 * <p>
 * Then, example usage: {@code Rave.getInstance().validate(myModelObject);}
 * </p>
 */
public class Rave {

    private static final int CLASS_VALIDATOR_DEFAULT_CACHE_SIZE = 100;
    private static final ExclusionStrategy EMPTY_EXCLUSION = new ExclusionStrategy.Builder().build();

    private final Map<Class<?>, BaseValidator> classValidatorMap = new HashMap<>();
    private final UnAnnotatedModelValidator unannotatedModelValidator =
            new UnAnnotatedModelValidator(CLASS_VALIDATOR_DEFAULT_CACHE_SIZE);

    /**
     * Get an instance of RAVE validator.
     *
     * @return the singleton instance of the RAVE validator.
     */
    @NonNull
    public static synchronized Rave getInstance() {
        return SingletonHolder.getInstance();
    }

    /**
     * Validate an object. If the object is not supported, nothing will happen. Otherwise the object will be routed to
     * the correct sub-validator which knows how to validate it.
     *
     * @param object the object to be validated.
     * @throws RaveException if validation fails.
     */
    public void validate(@NonNull Object object) throws RaveException {
        validate(object, EMPTY_EXCLUSION);
    }

    /**
     * Validate an object. If the object is not supported, nothing will happen. Otherwise the object will be routed to
     * the correct sub-validator which knows how to validate it.
     *
     * @param object the object to be validated.
     * @param exclusionStrategy the {@link ExclusionStrategy} that is used to ignore classes that are not required for
     * validation.
     * @throws RaveException if validation fails.
     */
    public void validate(@NonNull Object object, @NonNull ExclusionStrategy exclusionStrategy)
            throws RaveException {
        Class<?> clazz = object.getClass();
        Validated validated = clazz.getAnnotation(Validated.class);
        BaseValidator validator;
        synchronized (this) {
            if (validated == null && !unannotatedModelValidator.hasSeen(clazz)) {
                unannotatedModelValidator.processNonAnnotatedClasses(clazz);
            }
            validator = classValidatorMap.get(clazz);
            if (validator == null) {
                validator = getValidatorInstance(clazz);
            }
            if (validator == null) {
                throw new UnsupportedObjectException(Collections.singletonList(
                        new RaveError(clazz, "", RaveErrorStrings.CLASS_NOT_SUPPORTED_ERROR)));
            }
        }
        validator.validate(object, exclusionStrategy);
    }

    /**
     * This method is used to inject new validator objects in the global RAVE validation registry.
     *
     * @param validator the validator to add.
     * @param supportedModels the class types this validator supports.
     */
    synchronized void registerValidator(@NonNull BaseValidator validator, @NonNull Set<Class<?>> supportedModels) {
        for (Class<?> clazz : supportedModels) {
            BaseValidator base = classValidatorMap.put(clazz, validator);
            if (base != null) {
                throw new IllegalStateException("Two validators are validating the same model. "
                        + base.getClass().getCanonicalName() + " and "
                        + classValidatorMap.get(clazz).getClass().getCanonicalName() + " for class "
                        + clazz.getCanonicalName());
            }
        }
    }

    /**
     * Since various objects may have subclass/interfaces that also require validation this mechanism allows for
     * targeted validation of different inherited subtypes on the same object.
     *
     * @param obj the object to validate
     * @param clazz the class type to validate it as.
     * @param exclusionStrategy the {@link ExclusionStrategy} that is used to ignore classes that are not required for
     * validation.
     * @throws RaveException thrown if the validation process fails.
     */
    void validateAs(
            @NonNull Object obj,
            @NonNull Class<?> clazz,
            @NonNull ExclusionStrategy exclusionStrategy) throws RaveException {
        if (!clazz.isInstance(obj)) {
            throw new IllegalArgumentException("Trying to validate " + obj.getClass().getCanonicalName() + " as "
                    + clazz.getCanonicalName());
        }
        BaseValidator base;
        synchronized (this) {
            base = classValidatorMap.get(clazz);
            if (base == null) {
                base = getValidatorInstance(clazz);
            }
            if (base == null) {
                throw new UnsupportedObjectException(Collections.singletonList(
                        new RaveError(obj.getClass(), "", RaveErrorStrings.CLASS_NOT_SUPPORTED_ERROR)));
            }
        }
        base.validateAs(obj, clazz, exclusionStrategy);
    }

    private void registerValidatorWithClass(@NonNull BaseValidator validator, @NonNull Class<?> supportedModel) {
        classValidatorMap.put(supportedModel, validator);
    }

    private void removeEntry(@NonNull Class<?> supportedModel) {
        classValidatorMap.remove(supportedModel);
    }

    /**
     * Find and instantiate required {@link ValidatorFactory}.
     *
     * @param classToValidate the {@link Class} type to be validated.
     * @return BaseValidator that can validate this object or null if something goes wrong or there is no available
     * validator.
     */
    @Nullable
    private BaseValidator getValidatorInstance(@NonNull Class<?> classToValidate) {
        Validated validated = classToValidate.getAnnotation(Validated.class);
        if (validated == null) {
            return null;
        }
        Class<? extends ValidatorFactory> validatorClazz = validated.factory();
        try {
            validatorClazz.newInstance().generateValidator();
            return classValidatorMap.get(classToValidate);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Singleton holder for the rave instance.
     */
    private static class SingletonHolder {

        private static final Rave INSTANCE = new Rave();

        public static Rave getInstance() {
            return SingletonHolder.INSTANCE;
        }
    }

    /**
     * A {@link BaseValidator} that can handle objects that are not annotated with the {@link Validated} annotation.
     */
    static final class UnAnnotatedModelValidator extends BaseValidator {

        private final Map<Class<?>, Void> unsupportedClassesCache;
        private final Map<Class<?>, Set<Class<?>>> supportedClassesCache = new HashMap<>();

        UnAnnotatedModelValidator(final int cacheSize) {
            unsupportedClassesCache =
                    new LinkedHashMap<Class<?>, Void>() {
                    @Override
                    public boolean removeEldestEntry(Map.Entry<Class<?>, Void> eldest) {
                        boolean returnValue = size() > cacheSize;
                        if (returnValue) {
                            getInstance().removeEntry(eldest.getKey());
                        }
                        return returnValue;
                    }
                };
        }

        @Override
        protected void validateAs(
                @NonNull Object object,
                @NonNull Class<?> clazz,
                @NonNull ExclusionStrategy exclusionStrategy) throws RaveException {
            if (unsupportedClassesCache.containsKey(clazz)) {
                throw new UnsupportedObjectException(Collections.singletonList(
                        new RaveError(clazz, "", RaveErrorStrings.CLASS_NOT_SUPPORTED_ERROR)));
            }
            Set<Class<?>> inheritanceSet = supportedClassesCache.get(clazz);
            if (inheritanceSet == null || inheritanceSet.isEmpty()) {
                throw new IllegalArgumentException(clazz.getCanonicalName() + ":"
                        + RaveErrorStrings.CLASS_NOT_SUPPORTED_ERROR + this.getClass().getCanonicalName());
            }
            List<RaveError> raveErrors = null;
            for (Class<?> parentClass : inheritanceSet) {
                raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(parentClass, object, exclusionStrategy));
            }
            if (raveErrors != null && !raveErrors.isEmpty()) {
                throw new InvalidModelException(raveErrors);
            }
        }

        /**
         * Process any {@link Class} that is not annotated with the {@link Validated} annotation.
         *
         * @param clazz the {@link Class} to process.
         */
        void processNonAnnotatedClasses(@NonNull Class<?> clazz) {
            Validated validated = clazz.getAnnotation(Validated.class);
            if (validated != null) {
                throw new IllegalArgumentException(
                        clazz.getCanonicalName() + " is annotated with " + Validated.class.getCanonicalName());
            }
            if (!traverseClassHierarchy(clazz, clazz)) {
                unsupportedClassesCache.put(clazz, null);
            }
            addSupportedClass(clazz);
            getInstance().registerValidatorWithClass(this, clazz);
        }

        /**
         * Recursively traverse the inheritance tree until a {@link Validated} annotation is reached.
         *
         * @param original the original {@link Class} we are processing.
         * @param classToCheck the {@link Class} that we are checking which is in the inheritance tree of
         * {@code original}
         * @return true if any node in the inheritance tree is annotated with the {@link Validated} annotation. False
         * otherwise.
         */
        private boolean traverseClassHierarchy(@NonNull Class<?> original, @NonNull Class<?> classToCheck) {
            boolean returnValue = false;
            Class<?> parentClass = classToCheck.getSuperclass();
            if (parentClass != null) {
                returnValue = evaluateInheritance(original, parentClass);
            }
            for (Class<?> interfaces : classToCheck.getInterfaces()) {
                returnValue = evaluateInheritance(original, interfaces) || returnValue;
            }
            return returnValue;
        }

        /**
         * Evaluate an individual node in the inheritance tree.
         *
         * @param original the original {@link Class} we are processing.
         * @param classToCheck the {@link Class} that we are checking which is in the inheritance tree of
         * {@code original}
         * @return true if any node in the inheritance tree is annotated with the {@link Validated} annotation. False
         * otherwise.
         */
        private boolean evaluateInheritance(@NonNull Class<?> original, @NonNull Class<?> classToCheck) {
            Validated validated = classToCheck.getAnnotation(Validated.class);
            if (validated != null) {
                Set<Class<?>> set = supportedClassesCache.get(original);
                if (set == null) {
                    set = new HashSet<>();
                    supportedClassesCache.put(original, set);
                }
                set.add(classToCheck);
                return true;
            }
            return traverseClassHierarchy(original, classToCheck);
        }

        private boolean hasSeen(Class<?> clazz) {
            return supportedClassesCache.containsKey(clazz) || unsupportedClassesCache.containsKey(clazz);
        }
    }
}
