package com.wws.myrpc.server.locator;

import java.util.HashMap;
import java.util.Map;

public enum ServiceRegistry {

    INS;


    public Map<String, ServiceDescriptor> map = new HashMap<>();

    public void addServiceDescriptor(ServiceDescriptor serviceDescriptor){
        map.putIfAbsent(serviceDescriptor.getKey(), serviceDescriptor);
    }

    public ServiceDescriptor getServiceDescriptor(String key){
        return map.get(key);
    }

}
