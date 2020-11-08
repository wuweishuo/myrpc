package com.wws.myrpc.core.exception;

public class RpcException extends Exception {

    public RpcException(String message) {
        super(message);
    }

    public RpcException(Throwable e){
        super(e);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }
}
