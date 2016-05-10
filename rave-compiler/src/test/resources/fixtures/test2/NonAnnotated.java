package com.uber.rave.model;

import android.support.annotation.NonNull;

public class NonAnnotated extends com.uber.rave.model.AbstractAnnotated {

    private final String nonNullAbstractMethodString;

    public NonAnnotated(String nonNullAbstractMethodString) {
        this.nonNullAbstractMethodString = nonNullAbstractMethodString;
    }

    @NonNull
    @Override
    public String nonNullAbstractMethodString() {
        return nonNullAbstractMethodString;
    }
}
