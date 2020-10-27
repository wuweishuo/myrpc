package com.wws.myrpc.client.callback;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum CallbackPool {

    INS;

    private final Map<Long, CallbackContext> map = new ConcurrentHashMap<>();

    public void put(long flowId, CallbackContext callbackContext){
        map.putIfAbsent(flowId, callbackContext);
    }

    public CallbackContext get(long flowId){
        return map.get(flowId);
    }

}
