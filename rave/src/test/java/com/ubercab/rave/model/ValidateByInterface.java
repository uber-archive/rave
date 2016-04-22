package com.ubercab.rave.model;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.ubercab.rave.annotation.Validated;

/**
 * Example of an interface using RAVE.
 */
@Validated(factory = TestFactory.class)
public interface ValidateByInterface {

    /**
     * @return Example of a method using RAVE.
     */
    @NonNull
    @Size(min = 0, max = 4)
    String getNonNullString();
}
