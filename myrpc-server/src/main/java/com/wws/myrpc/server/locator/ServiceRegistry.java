package com.wws.myrpc.server.locator;

import java.util.HashMap;
import java.util.Map;

/**
 * ServiceRegistry
 * 服务注册器
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public enum ServiceRegistry {

    INS;

    private final Map<String, ServiceDescriptor> map = new HashMap<>();

    /**
     * 注册服务描述
     *
     * @param serviceDescriptor ServiceDescriptor
     */
    public void addServiceDescriptor(ServiceDescriptor serviceDescriptor) {
        map.putIfAbsent(serviceDescriptor.getKey(), serviceDescriptor);
    }

    /**
     * 获取服务描述
     *
     * @param key methodName(args class),例如 add(int, int)
     * @return ServiceDescriptor
     */
    public ServiceDescriptor getServiceDescriptor(String key) {
        return map.get(key);
    }

}
