package com.wws.myrpc.core.exception;

/**
 * NoLongException
 * 标示包未完全达到服务端
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class NoLongException extends Throwable {

    public NoLongException(String message) {
        super(message);
    }
}
