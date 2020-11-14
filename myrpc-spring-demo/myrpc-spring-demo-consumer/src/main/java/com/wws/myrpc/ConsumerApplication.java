package com.wws.myrpc;

import com.wws.myrpc.client.RpcClientScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RpcClientScan(basePackages = "com.wws.myrpc.client")
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
