package com.wws.myrpc.client;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {

    String ip() default "";

    int port() default 0;

    String name() default "";

}
