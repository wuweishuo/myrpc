package com.wws.myrpc.registry;

import com.wws.myrpc.spi.SPI;

/**
 * RegistryServiceFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
@SPI("zookeeper")
public interface RegistryServiceFactory {

    RegistryService connect(RegistryProperties properties) throws Exception;

    void destroy(RegistryService registryService) throws Exception;

}
