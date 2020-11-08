package com.wws.myrpc.server.locator;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ServiceDescriptor {

    private String key;

    private Method method;

    private Object target;

    private Type[] parameterTypes;

    private Type returnType;

    @Override
    public String toString() {
        return "ServiceDescriptor{" +
                "key='" + key + '\'' +
                ", method=" + method +
                ", target=" + target +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", returnType=" + returnType +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Type[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Type[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }
}
