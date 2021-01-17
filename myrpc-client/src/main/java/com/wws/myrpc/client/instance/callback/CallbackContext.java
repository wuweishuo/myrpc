package com.wws.myrpc.client.instance.callback;

import java.lang.reflect.Type;

/**
 * CallbackContext
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class CallbackContext {


    private CallbackFuture<?> callbackFuture;

    /**
     * 请求的返回类型
     */
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
