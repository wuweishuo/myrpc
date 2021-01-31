package com.wws.myrpc.server;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(RpcServerProperties.class)
public class EnableRpcServerConfiguration {

    @Bean(destroyMethod = "shutdown")
    public Server server(RpcServerProperties rpcServerProperties) {
        try {
            Server server;
            if(rpcServerProperties.isRegister()) {
                server = new Server(rpcServerProperties.getServerProperties());
            }else{
                server = new Server(rpcServerProperties.getPort());
            }
            server.start();
            return server;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
