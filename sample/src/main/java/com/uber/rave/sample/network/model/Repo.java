package com.uber.rave.sample.network.model;

import android.support.annotation.NonNull;

import com.uber.rave.annotation.Validated;
import com.uber.rave.sample.network.RaveValidatorFactory;

@Validated(factory = RaveValidatorFactory.class)
public class Repo {

    private int id;
    @NonNull private Owner owner;
    @NonNull private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(@NonNull Owner owner) {
        this.owner = owner;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
