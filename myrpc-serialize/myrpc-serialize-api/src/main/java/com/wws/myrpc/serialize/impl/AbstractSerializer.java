package com.wws.myrpc.serialize.impl;

import com.wws.myrpc.serialize.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * AbstractSerializer
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-28
 */
public abstract class AbstractSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serialize(out, obj);
        out.close();
        return out.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        in.close();
        return deserialize(in, clazz);
    }
}
