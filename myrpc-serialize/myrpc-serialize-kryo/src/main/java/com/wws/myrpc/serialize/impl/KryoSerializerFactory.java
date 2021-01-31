package com.wws.myrpc.serialize.impl;

import com.wws.myrpc.serialize.Serializer;
import com.wws.myrpc.serialize.SerializerFactory;

/**
 * KryoSerializerFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-29
 */
public class KryoSerializerFactory implements SerializerFactory {

    private volatile KryoSerializer serializer;

    @Override
    public Serializer getInstance() {
        if(serializer == null){
            synchronized (this){
                if (serializer == null){
                    serializer = new KryoSerializer();
                }
            }
        }
        return serializer;
    }
}
