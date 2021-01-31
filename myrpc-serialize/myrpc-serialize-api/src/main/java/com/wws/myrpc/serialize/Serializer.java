package com.wws.myrpc.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Serializer
 * 序列化协议
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public interface Serializer {

    /**
     * 序列化到OutputStream
     *
     * @param out
     * @param obj
     * @throws IOException
     */
    void serialize(OutputStream out, Object obj) throws IOException;

    /**
     * 反序列化到InputStream
     *
     * @param in
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    <T> T deserialize(InputStream in, Class<T> clazz) throws IOException, ClassNotFoundException;

    /**
     * 序列化到byte[]
     *
     * @param obj
     * @return
     * @throws IOException
     */
    byte[] serialize(Object obj) throws IOException;

    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException;

}
