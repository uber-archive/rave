package com.uber.rave.sample.github.model;

import android.support.annotation.NonNull;

import com.uber.rave.annotation.Validated;
import com.uber.rave.sample.github.RaveValidatorFactory;

@Validated(factory = RaveValidatorFactory.class)
public class Owner {

    @NonNull private String login;
    private int id;

    @NonNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
