package com.wws.myrpc.util;

import java.io.*;

public class SerializationUtil {

    public static Object deserialize(byte[] arr) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(arr));
        return in.readObject();
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
        out.writeObject(obj);
        return byteArrayOutputStream.toByteArray();
    }

}
