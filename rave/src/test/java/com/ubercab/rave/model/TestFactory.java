package com.ubercab.rave.model;

import android.support.annotation.NonNull;

import com.ubercab.rave.BaseValidator;
import com.ubercab.rave.ValidatorFactory;

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
