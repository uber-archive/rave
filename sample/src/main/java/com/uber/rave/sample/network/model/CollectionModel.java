package com.uber.rave.sample.network.model;

import com.uber.rave.annotation.Validated;
import com.uber.rave.sample.network.RaveValidatorFactory;

import java.util.List;

@Validated(factory = RaveValidatorFactory.class)
public class CollectionModel {

    public List<Owner> getListOwners() {
        return foo;
    }

    public Owner getOwner() {
        return owner;
    }

    List<Owner> foo;

    Owner owner;
    public CollectionModel(List<Owner> foo) {
        this.foo = foo;
    }
}
