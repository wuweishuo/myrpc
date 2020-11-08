package com.wws.myrpc.core.protocol;

import java.io.Serializable;

public class Response implements Serializable {

    private Throwable exception;

    private Object result;

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
