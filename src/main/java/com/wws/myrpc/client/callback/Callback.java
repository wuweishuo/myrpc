package com.wws.myrpc.client.callback;

public interface Callback<T> {

    void setResult(T result);

    void setError(Throwable throwable);

}
