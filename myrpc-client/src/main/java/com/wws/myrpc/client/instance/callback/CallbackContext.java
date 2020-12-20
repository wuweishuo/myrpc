package com.wws.myrpc.client.instance.callback;

import java.lang.reflect.Type;

public class CallbackContext {

    private CallbackFuture<?> callbackFuture;

    private Type returnType;

    public CallbackContext(CallbackFuture<?> callbackFuture, Type returnType) {
        this.callbackFuture = callbackFuture;
        this.returnType = returnType;
    }

    public CallbackFuture<?> getCallbackFuture() {
        return callbackFuture;
    }

    public void setCallbackFuture(CallbackFuture<?> callbackFuture) {
        this.callbackFuture = callbackFuture;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }
}
