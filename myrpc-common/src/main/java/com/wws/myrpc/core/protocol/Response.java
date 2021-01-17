package com.wws.myrpc.core.protocol;

import java.io.Serializable;

/**
 * Response
 * 响应包body
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class Response implements Serializable {

    /**
     * 异常
     */
    private Throwable exception;

    /**
     * 返回值
     */
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
