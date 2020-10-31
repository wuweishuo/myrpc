package com.wws.myrpc.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {

    void serialize(OutputStream out, Object obj) throws IOException;

    <T> T deserialize(InputStream in, Class<T> clazz) throws IOException, ClassNotFoundException;

    byte[] serialize(Object obj) throws IOException;

    <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException;

}
