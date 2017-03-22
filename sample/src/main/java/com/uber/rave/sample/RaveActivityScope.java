package com.uber.rave.sample;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates a Dagger binding should always return the same object when injecting {@link RaveActivity}.
 */
@Retention(RUNTIME)
@Documented
@Scope
public @interface RaveActivityScope { }
