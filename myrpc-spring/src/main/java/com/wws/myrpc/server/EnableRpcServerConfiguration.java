package com.wws.myrpc.server;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(RpcServerProperties.class)
public class EnableRpcServerConfiguration {

    @Bean
    public Server server(RpcServerProperties rpcServerProperties){
        Integer port = rpcServerProperties.getPort();
        Server server = new Server(port);
        try {
            server.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return server;
    }

}
