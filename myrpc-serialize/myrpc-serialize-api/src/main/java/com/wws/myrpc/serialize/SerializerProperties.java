package com.wws.myrpc.serialize;

import com.wws.myrpc.spi.SPIProperties;

/**
 * SerializerProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-04
 */
public class SerializerProperties extends SPIProperties {

    public static final String SERIALIZER_NAME = "serializer.name";

    public SerializerProperties(String name) {
        setProperty(SERIALIZER_NAME, name);
    }

}
