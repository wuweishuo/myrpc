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

    private static final String URL = "url";

    public RegistryProperties(String name, String url) {
        super("registry");
        setName(name);
        setProperty(URL, url);
    }

    public String getUrl() {
        return getProperty(URL);
    }
}
