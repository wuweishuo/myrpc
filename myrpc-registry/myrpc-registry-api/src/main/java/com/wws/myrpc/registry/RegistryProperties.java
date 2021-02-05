package com.wws.myrpc.registry;

import com.wws.myrpc.spi.SPIProperties;

/**
 * RegistryProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-31
 */
public class RegistryProperties extends SPIProperties {

    public static final String SERVER_NAME = "registry.name";

    public static final String SERVER_ADDR = "registry.addr";

    public RegistryProperties(String name, String url) {
        setProperty(SERVER_NAME, name);
        setProperty(SERVER_ADDR, url);
    }
}
