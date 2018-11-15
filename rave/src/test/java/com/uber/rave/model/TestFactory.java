package com.uber.rave.model;

import androidx.annotation.NonNull;

import com.uber.rave.BaseValidator;
import com.uber.rave.ValidatorFactory;

/**
 * An example of how to create a {@link ValidatorFactory} class in a given library.
 */
public final class TestFactory implements ValidatorFactory {

    @NonNull
    @Override
    public BaseValidator generateValidator() {
        return new TestFactory_Generated_Validator();
    }
}
