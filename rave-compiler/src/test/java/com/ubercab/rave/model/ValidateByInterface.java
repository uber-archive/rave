package com.ubercab.rave.model;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.ubercab.rave.annotation.Validated;
import com.ubercab.rave.compiler.MyFactory;

/**
 * Example of an interface using RAVE.
 */
@Validated(factory = MyFactory.class)
public interface ValidateByInterface {

    /**
     * @return Example of a method using RAVE.
     */
    @NonNull
    @Size(min = 0, max = 4)
    String getNonNullString();
}
