package com.uber.rave.sample.github;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.uber.rave.Rave;
import com.uber.rave.RaveException;
import com.uber.rave.sample.github.model.Owner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This uses the default Java serializer.
 */
public class OwnerStorage {
    byte[] serializedOwner = new byte[0];

    public void storeOwner(Owner owner) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(owner);
            out.flush();
            serializedOwner = bos.toByteArray();
        } catch (IOException ignored) {

        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    public Owner getOwner() {
        if (serializedOwner.length == 0) {
            return null;
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(serializedOwner);
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(bis);
            Owner deserializedOwner = (Owner) in.readObject();
            Rave.getInstance().validate(deserializedOwner);
            return deserializedOwner;
        } catch (ClassNotFoundException | IOException e) {
            return null;
        } catch (RaveException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }
}
