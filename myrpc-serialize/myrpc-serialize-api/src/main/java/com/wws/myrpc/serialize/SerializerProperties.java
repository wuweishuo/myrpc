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

    public SerializerProperties(String name) {
        super("serializer");
        setName(name);
    }

}
