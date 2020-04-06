package com.wws.myrpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    String ip;

    int port;

    Bootstrap bootstrap;
    NioEventLoopGroup workerGroup;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;

        this.bootstrap = new Bootstrap();
        this.workerGroup = new NioEventLoopGroup();
        bootstrap.channel(NioSocketChannel.class)
                .group(workerGroup)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                    }
                });
    }

    public void connect() throws InterruptedException {
        bootstrap.connect(ip, port).sync();
    }
}
