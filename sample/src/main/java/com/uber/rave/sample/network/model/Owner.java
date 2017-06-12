package com.uber.rave.sample.network.model;

import android.support.annotation.NonNull;

import com.uber.rave.InvalidModelException;
import com.uber.rave.annotation.MustBeTrue;
import com.uber.rave.annotation.Validated;
import com.uber.rave.sample.network.RaveValidatorFactory;
import com.uber.rave.sample.network.RaveValidatorFactory_Generated_Validator;

import java.io.Serializable;
import java.util.List;

@Validated(factory = RaveValidatorFactory.class)
public class Owner implements Serializable {
    private static final String finalstring = "";
    @NonNull private String login;
    @MustBeTrue private boolean field2;
    private final List<Owner> list = null;
    private int id;

    public Owner() {
        login = "";
        field2 = false;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Owner owner = (Owner) o;

        if (id != owner.id) return false;
        return login != null ? login.equals(owner.login) : owner.login == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    // This is an example of how you can check inner fields that are protected or private. We will soon eliminat the
    // need to even do this in later RAVE releases.
    @MustBeTrue
    public boolean validateFields() throws InvalidModelException {
        RaveValidatorFactory_Generated_Validator.validateInternalFor_com_uber_rave_sample_network_model_Owner(login,
                field2, list);
        return true;
    }
}
