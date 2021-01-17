package com.wws.myrpc.server.locator;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * ServiceDescriptor
 * 描述一个服务方法
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class ServiceDescriptor {

    /**
     * 方法的唯一标示， methodName(args class),例如 add(int, int)
     */
    private String key;

    /**
     * 服务方法
     */
    private Method method;

    /**
     * 服务实例
     */
    private Object target;

    /**
     * 方法参数
     */
    private Type[] parameterTypes;

    /**
     * 方法返回类型
     */
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
