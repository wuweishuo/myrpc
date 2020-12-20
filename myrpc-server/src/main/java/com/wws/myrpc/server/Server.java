package com.wws.myrpc.server;

import com.wws.myrpc.core.handler.ProtocolDecoder;
import com.wws.myrpc.core.handler.ProtocolEncoder;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.RegistryServiceFactory;
import com.wws.myrpc.registry.ServerInfo;
import com.wws.myrpc.server.handler.ServerHandler;
import com.wws.myrpc.server.locator.ServiceLocator;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetAddress;

public class Server {

    private final ServerBootstrap serverBootstrap;
    private final NioEventLoopGroup bossGroup;
    private final NioEventLoopGroup workerGroup;

    private RegistryService registryService;

    private final int port;

    private final String name;

    private final boolean isRegister;

    private static final int IDLE_TIMEOUT = 3;

    public Server(int port) throws Exception {
        this(port, "myrpc", false, null);
    }

    public Server(int port, String name, boolean isRegister, String registerUrl) throws Exception {
        this.name = name;
        this.isRegister = isRegister;
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new IdleStateHandler(0, 0, IDLE_TIMEOUT))
                                .addLast(new ProtocolDecoder())
                                .addLast(new ServerHandler())
                                .addLast(new ProtocolEncoder());
                    }
                });
        this.port = port;
        if(isRegister) {
            registryService = RegistryServiceFactory.getInstance("zookeeper", registerUrl);
        }
    }

    public void start() throws Exception {
        serverBootstrap.bind(port).sync();
        System.out.println("server listen in " + port + "......");
        if(isRegister) {
            registryService.register(new ServerInfo(name, "127.0.0.1", port));
        }
    }

    public void shutdown() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }


    public void registerService(Class clazz, Object service) {
        ServiceLocator.INS.register(clazz, service);
    }

}
