package com.wws.myrpc.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * AbstractRegistryServiceFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-31
 */
public abstract class AbstractRegistryServiceFactory<T extends RegistryService> implements RegistryServiceFactory {

    private final Map<String, RegistryService> instances = new HashMap<>();

    private RegistryProperties properties;

    public AbstractRegistryServiceFactory(RegistryProperties properties) {
        this.properties = properties;
    }

    @Override
    public RegistryService connect(RegistryProperties properties) throws Exception {
        String url = properties.getUrl();
        RegistryService registryService = instances.get(url);
        if (registryService == null) {
            synchronized (this) {
                registryService = instances.get(url);
                if (registryService == null) {
                    registryService = getInstance(properties);
                    instances.put(url, registryService);
                }
            }
        }
        return registryService;
    }

    public abstract T getInstance(RegistryProperties properties) throws Exception;

    @Override
    public void destroy(RegistryService registryService) throws Exception {
        instances.remove(registryService.getUrl());
        registryService.destroy();
    }
}
