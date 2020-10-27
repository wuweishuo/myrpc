package com.wws.myrpc.client.callback;

import junit.framework.TestCase;

import java.util.concurrent.ExecutionException;

public class CallbackFutureTest extends TestCase {

    public void testGet() throws ExecutionException, InterruptedException {
        CallbackFuture callbackFuture = new CallbackFuture();
        callbackFuture.setResult("test");
        System.out.println(callbackFuture.get());

        CallbackFuture callbackFuture2 = new CallbackFuture();
        callbackFuture2.setError(new Throwable("test"));
        System.out.println(callbackFuture2.get());
    }


}