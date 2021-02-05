package com.wws.myrpc.registry.zookeeper;

import com.wws.myrpc.registry.AbstractRegistryServiceFactory;
import com.wws.myrpc.registry.RegistryProperties;

/**
 * ZookeeperRegistryServiceFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-31
 */
public class ZookeeperRegistryServiceFactory extends AbstractRegistryServiceFactory<ZookeeperRegistryService> {

    public ZookeeperRegistryServiceFactory(RegistryProperties properties) {
        super(properties);
    }

    @Override
    public ZookeeperRegistryService getInstance(RegistryProperties properties) throws Exception {
        return new ZookeeperRegistryService(properties.getProperty(RegistryProperties.SERVER_ADDR));
    }
}
