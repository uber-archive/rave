package com.ubercab.rave.model;

import android.support.annotation.NonNull;

import com.ubercab.rave.annotation.Validated;

@Validated(factory = TestFactory.class)
public abstract class AbstractAnnotated {
    @NonNull
    public abstract String nonNullAbstractMethodString();

    @NonNull
    public String nonNullString() {
        return "Non Null from abstract";
    }
}
