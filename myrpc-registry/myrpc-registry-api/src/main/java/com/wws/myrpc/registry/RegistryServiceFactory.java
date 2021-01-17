package com.wws.myrpc.registry;

import com.wws.myrpc.spi.ExtensionLoaderFactory;

/**
 * RegistryServiceFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class RegistryServiceFactory {

    /**
     * 通过spi获取注册中心
     *
     * @param name
     * @param addr
     * @return
     * @throws Exception
     */
    public static RegistryService getInstance(String name, String addr) throws Exception {
        RegistryService registryService = ExtensionLoaderFactory.load(RegistryService.class, name);
        registryService.connect(addr);
        return registryService;
    }

}
