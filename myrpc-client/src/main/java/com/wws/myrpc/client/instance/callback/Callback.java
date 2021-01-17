package com.wws.myrpc.client.instance.callback;

/**
 * Callback
 * 请求回调
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public interface Callback<T> {

    /**
     * 保存返回值
     * @param result 返回值
     */
    void setResult(T result);

    /**
     * 保存返回的调用异常
     * @param throwable 调用异常
     */
    void setError(Throwable throwable);

}
