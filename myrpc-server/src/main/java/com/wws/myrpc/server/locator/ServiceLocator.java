package com.wws.myrpc.server.locator;

import com.wws.myrpc.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public enum ServiceLocator {

    INS;

    public void register(Class clazz, Object service){
        Method[] methods = ReflectionUtil.getAllInstanceMethods(clazz);
        for (Method method : methods) {
            Type[] parameterTypes = method.getGenericParameterTypes();
            Type returnType = method.getGenericReturnType();
            ServiceDescriptor serviceDescriptor = new ServiceDescriptor();
            serviceDescriptor.setKey(method.toGenericString());
            serviceDescriptor.setMethod(method);
            serviceDescriptor.setTarget(service);
            serviceDescriptor.setParameterTypes(parameterTypes);
            serviceDescriptor.setReturnType(returnType);
            ServiceRegistry.INS.addServiceDescriptor(serviceDescriptor);
        }
    }

    public ServiceDescriptor get(String key){
        return ServiceRegistry.INS.getServiceDescriptor(key);
    }

}
