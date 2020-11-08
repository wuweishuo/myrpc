package com.wws.myrpc.client.callback;

import java.util.HashMap;
import java.util.Map;

public class CallbackContextMap {

    private final Map<Long, CallbackContext> map = new HashMap<>();

    public void put(long flowId, CallbackContext callbackContext){
        map.putIfAbsent(flowId, callbackContext);
    }

    public CallbackContext get(long flowId){
        return map.get(flowId);
    }

    public void remove(long flowId){
        map.remove(flowId);
    }

}
