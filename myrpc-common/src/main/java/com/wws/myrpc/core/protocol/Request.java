package com.wws.myrpc.core.protocol;

import java.io.Serializable;

public class Request implements Serializable {

    /**
     * 标示调用方法
     */
    private String method;

    /**
     * 请求参数
     */
    private Object[] args;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
