package com.wws.myrpc.client;

import com.wws.myrpc.client.proxy.JdkProxyFactory;
import com.wws.myrpc.client.proxy.ProxyFactory;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RpcClientScannerRegistrar.class)
public @interface RpcClientScan {

    @AliasFor("basePackages")
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<? extends ProxyFactory> proxy() default JdkProxyFactory.class;

}
