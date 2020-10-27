package com.wws.myrpc.serialization;

import java.io.IOException;
import java.lang.reflect.Type;

public interface Serializer {

    Object[] deserialize(Type[] argTypes, byte[] bytes) throws IOException, ClassNotFoundException;

    byte[] serialize(Type[] argTypes, Object[] args) throws IOException;

}
