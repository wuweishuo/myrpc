package com.wws.myrpc.core.protocol;

import java.io.Serializable;

/**
 * Request
 * 请求包body
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class Request implements Serializable {

    /**
     * 标示调用方法 methodName(args class),例如 add(int, int)
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
