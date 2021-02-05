package com.wws.myrpc.client.cluster;

import com.wws.myrpc.spi.SPIProperties;

/**
 * ClusterProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-04
 */
public class ClusterProperties extends SPIProperties {

    public static final String CLUSTER_NAME = "cluster.name";

    public ClusterProperties(String name) {
        setProperty(CLUSTER_NAME, name);
    }

}
