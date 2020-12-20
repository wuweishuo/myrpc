package com.wws.myrpc.client.proxy;

import com.wws.myrpc.client.IClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcClientHandler implements InvocationHandler {

    private IClient client;

    public RpcClientHandler(IClient client) {
        this.client = client;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(proxy, args);
        }
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            if ("toString".equals(methodName)) {
                return proxy.toString();
            } else if ("hashCode".equals(methodName)) {
                return proxy.hashCode();
            }
        } else if (parameterTypes.length == 1 && "equals".equals(methodName)) {
            return proxy.equals(args[0]);
        }
        return client.transport(method, method.getReturnType(), args);
    }
}
