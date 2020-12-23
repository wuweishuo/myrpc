package com.wws.myrpc.registry;

import java.util.Iterator;
import java.util.ServiceLoader;

public class RegistryServiceFactory {

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
