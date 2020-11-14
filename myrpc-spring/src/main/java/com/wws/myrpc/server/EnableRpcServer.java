package com.wws.myrpc.server;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EnableRpcServerConfiguration.class, RpcServiceBeanPostProcessor.class})
public @interface EnableRpcServer {

}
