package com.uber.rave.sample;

import com.uber.rave.sample.github.model.Owner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This uses the default Java serializer.
 */
class DefaultSerializer {

    static byte[] serialize(Owner owner) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(owner);
            out.flush();
            return bos.toByteArray();
        } catch (IOException ignored) {
            return new byte[0];
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    static Owner deserialize(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(bis);
            return (Owner) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            return null;
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
