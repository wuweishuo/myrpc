package com.wws.myrpc.client;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {

    String ip() default "";

    int port() default 0;

    String name() default "";

    String registryName() default "zookeeper";

    String registerUrl() default "";

    String clusterName() default "failFast";

    String loadBalanceName() default "random";

    String serializerName() default "jdk";

}
