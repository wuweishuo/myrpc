package com.wws.myrpc.client.callback;

import junit.framework.TestCase;

public class CallbackPoolTest extends TestCase {

    public void testGet() {
        CallbackPool.INS.put(1L, new CallbackContext(new CallbackFuture<>(), String.class));
        CallbackContext callbackContext = CallbackPool.INS.get(1L);
        System.out.println(callbackContext.getReturnType());
    }
}