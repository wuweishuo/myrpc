package com.wws.myrpc.client.cluster.loadbalance;

import com.wws.myrpc.spi.SPIProperties;

/**
 * LoadBalanceProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-04
 */
public class LoadBalanceProperties extends SPIProperties {

    public static final String LOAD_BALANCE_ADDR = "server.addr";

    public LoadBalanceProperties(String name) {
        setProperty(LOAD_BALANCE_ADDR, name);
    }

}
