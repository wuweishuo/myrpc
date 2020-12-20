package com.wws.myrpc.server;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(RpcServerProperties.class)
public class EnableRpcServerConfiguration {

    @Bean
    public Server server(RpcServerProperties rpcServerProperties){
        try {
            Server server;
            if(rpcServerProperties.getRegister()){
                server = new Server(rpcServerProperties.getPort(), rpcServerProperties.getName(), rpcServerProperties.getRegister(), rpcServerProperties.getRegisterUri());
            }else {
                Integer port = rpcServerProperties.getPort();
                server = new Server(port);
            }
            server.start();
            return server;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
