package com.wws.myrpc.client.instance.callback;

public interface Callback<T> {

    void setResult(T result);

    void setError(Throwable throwable);

}
