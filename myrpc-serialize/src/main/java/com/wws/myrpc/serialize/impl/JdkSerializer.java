package com.wws.myrpc.serialize.impl;

import com.wws.myrpc.serialize.Serializer;

import java.io.*;

/**
 * JdkSerializer
 * jdk原生序列化协议
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class JdkSerializer implements Serializer {

    @Override
    public void serialize(OutputStream out, Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(InputStream in, Class<T> clazz) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        return (T) ois.readObject();
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serialize(out, obj);
        return out.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        return deserialize(in, clazz);
    }
}
