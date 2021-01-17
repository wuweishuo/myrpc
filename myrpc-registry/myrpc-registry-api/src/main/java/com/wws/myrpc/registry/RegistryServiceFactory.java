package com.wws.myrpc.registry;

import java.util.Iterator;
import java.util.ServiceLoader;

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
     * @param name todo
     * @param addr
     * @return
     * @throws Exception
     */
    public static RegistryService getInstance(String name, String addr) throws Exception {
        ServiceLoader<RegistryService> serviceLoader = ServiceLoader.load(RegistryService.class);
        Iterator<RegistryService> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            RegistryService next = iterator.next();
            next.connect(addr);
            return next;
        }
        return null;

    }

}
