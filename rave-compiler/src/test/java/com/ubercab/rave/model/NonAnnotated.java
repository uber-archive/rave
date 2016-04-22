package com.ubercab.rave.model;

import android.support.annotation.NonNull;

public class NonAnnotated extends AbstractAnnotated {

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
