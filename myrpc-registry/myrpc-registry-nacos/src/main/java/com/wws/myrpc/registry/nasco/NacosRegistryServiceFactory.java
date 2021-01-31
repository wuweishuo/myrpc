package com.wws.myrpc.registry.nasco;

import com.wws.myrpc.registry.AbstractRegistryServiceFactory;
import com.wws.myrpc.registry.RegistryProperties;

/**
 * NacosRegistryServiceFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-31
 */
public class NacosRegistryServiceFactory extends AbstractRegistryServiceFactory<NacosRegistryService> {
    @Override
    public NacosRegistryService getInstance(RegistryProperties properties) throws Exception {
        return new NacosRegistryService(properties.getProperty(RegistryProperties.SERVER_ADDR));
    }
}
