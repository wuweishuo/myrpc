package com.wws.myrpc.serialization;

import com.wws.myrpc.util.SerializationUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class JdkSerializer implements Serializer {

    @Override
    public Object[] deserialize(Type[] argTypes, byte[] bytes) throws IOException, ClassNotFoundException {
        List list = (List)SerializationUtil.deserialize(bytes);
        return list.toArray(new Object[0]);
    }

    @Override
    public byte[] serialize(Type[] argTypes, Object[] args) throws IOException {
        List<Object> list = Arrays.asList(args);
        return SerializationUtil.serialize(list);
    }
}
