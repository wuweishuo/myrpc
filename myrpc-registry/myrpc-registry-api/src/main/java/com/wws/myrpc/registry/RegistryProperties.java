package com.wws.myrpc.registry;

import java.util.Properties;

/**
 * RegistryProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-31
 */
public class RegistryProperties extends Properties {

    public static final String SERVER_ADDR = "server.addr";

    public RegistryProperties(String url) {
        setProperty(SERVER_ADDR, url);
    }
}
