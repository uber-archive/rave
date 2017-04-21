package com.uber.rave;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate what assumptions RAVE should make when generating validation code for a
 * {@link ValidatorFactory}. This annotation should be applied to your validator factory.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Validator {

    /**
     * @return indicates what kind of assumptions RAVE should make when generating validation code at compile time.
     */
    Mode mode() default Mode.DEFAULT;

    /**
     * Indicates different kinds of modes RAVE can run in.
     */
    enum Mode {
        /**
         * RAVE will assume if a method is not annotated its return type is {@link Nullable}.
         */
        DEFAULT,
        /**
         * RAVE will assume if a method is not annotated, its return type is {@link NonNull}.
         */
        STRICT
    }
}
