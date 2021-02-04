package com.wws.myrpc.serialize;

import com.wws.myrpc.spi.SPI;

/**
 * SerializerFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-29
 */

public interface SerializerFactory {

    Serializer getInstance(SerializerProperties properties);

}
