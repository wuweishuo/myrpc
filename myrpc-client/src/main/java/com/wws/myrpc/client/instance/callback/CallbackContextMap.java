package com.wws.myrpc.client.instance.callback;

import java.util.HashMap;
import java.util.Map;

/**
 * CallbackContextMap
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class CallbackContextMap {

    /**
     * flowId->CallbackContext
     */
    private final Map<Long, CallbackContext> map = new HashMap<>();

    public void put(long flowId, CallbackContext callbackContext) {
        map.putIfAbsent(flowId, callbackContext);
    }

    public CallbackContext get(long flowId) {
        return map.get(flowId);
    }

    public void remove(long flowId) {
        map.remove(flowId);
    }

}
