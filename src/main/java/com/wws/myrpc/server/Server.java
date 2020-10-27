package com.wws.myrpc.server;

import com.wws.myrpc.core.handler.ProtocolDecoder;
import com.wws.myrpc.core.handler.ProtocolEncoder;
import com.wws.myrpc.server.handler.ServiceInvokeHandler;
import com.wws.myrpc.server.locator.ServiceLocator;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    private int port;

    public Server(int port) {
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ProtocolDecoder());
                        socketChannel.pipeline().addLast(new ServiceInvokeHandler());
                        socketChannel.pipeline().addLast(new ProtocolEncoder());
                    }
                });
        this.port = port;
    }

    public void start() throws InterruptedException {
        serverBootstrap.bind(port).sync();
        System.out.println("server listen in "+ port + "......");
    }

    public void shutdown() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }


    public void registerService(Class clazz, Object service){
        ServiceLocator.INS.register(clazz, service);
    }

}
