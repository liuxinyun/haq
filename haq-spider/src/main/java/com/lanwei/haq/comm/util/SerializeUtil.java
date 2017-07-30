package com.lanwei.haq.comm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public final class SerializeUtil {

    public static byte[] serialize(Object object) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais);) {
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
