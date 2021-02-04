package com.wws.myrpc.serialize.impl;

import com.wws.myrpc.serialize.Serializer;
import com.wws.myrpc.serialize.SerializerFactory;
import com.wws.myrpc.serialize.SerializerProperties;

/**
 * JdkSerializerFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-29
 */
public class JdkSerializerFactory implements SerializerFactory {
    private volatile JdkSerializer serializer;

    @Override
    public Serializer getInstance(SerializerProperties properties) {
        if(serializer == null){
            synchronized (this){
                if (serializer == null){
                    serializer = new JdkSerializer();
                }
            }
        }
        return serializer;
    }
}
