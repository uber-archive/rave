package com.uber.rave.sample.network;

import android.support.annotation.NonNull;

import com.uber.rave.BaseValidator;
import com.uber.rave.ValidatorFactory;

/**
 * A factory class capable of creating a validator whose implementation generated at annotation processing time.
 */
public final class RaveValidatorFactory implements ValidatorFactory {

    @NonNull
    @Override
    public BaseValidator generateValidator() {
        return new RaveValidatorFactory_Generated_Validator();
    }
}
