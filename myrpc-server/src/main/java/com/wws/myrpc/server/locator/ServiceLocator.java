package com.wws.myrpc.server.locator;

import com.wws.myrpc.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * ServiceLocator
 * 服务定位器
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public enum ServiceLocator {

    INS;

    /**
     * 注册服务
     *
     * @param clazz   server class
     * @param service 服务实例
     */
    public void register(Class<?> clazz, Object service) {
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

    /**
     * 获取服务方法描述
     *
     * @param key methodName(args class),例如 add(int, int)
     * @return ServiceDescriptor
     */
    public ServiceDescriptor get(String key) {
        return ServiceRegistry.INS.getServiceDescriptor(key);
    }

}
