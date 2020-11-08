package com.wws.myrpc.core.exception;

public class ServiceNotFoundException extends RpcException{

    public ServiceNotFoundException(String methodName) {
        super(String.format("no such service:{%s}", methodName));
    }
}
