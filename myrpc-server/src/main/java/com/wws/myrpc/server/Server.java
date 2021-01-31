package com.wws.myrpc.server;

import com.wws.myrpc.core.handler.ProtocolDecoder;
import com.wws.myrpc.core.handler.ProtocolEncoder;
import com.wws.myrpc.registry.RegistryService;
import com.wws.myrpc.registry.RegistryServiceFactory;
import com.wws.myrpc.registry.ServerInfo;
import com.wws.myrpc.serialize.Serializer;
import com.wws.myrpc.serialize.SerializerFactory;
import com.wws.myrpc.server.handler.ServerHandler;
import com.wws.myrpc.server.locator.ServiceLocator;
import com.wws.myrpc.spi.ExtensionLoaderFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server
 * 服务端
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private final ServerBootstrap serverBootstrap;
    private final NioEventLoopGroup bossGroup;
    private final NioEventLoopGroup workerGroup;

    private final int port;

    private ServerProperties properties;
    private RegistryService registryService;

    private static final int IDLE_TIMEOUT = 3;

    /**
     * 仅暴露接口，不向注册中心注册服务
     *
     * @param port 端口
     * @throws Exception
     */
    public Server(int port){
        this(port, "jdk");
    }

    /**
     * @param properties        ServerProperties
     * @throws Exception
     */
    public Server(ServerProperties properties) throws Exception {
        this(properties.getPort(), properties.getSerializerName());
        this.properties = properties;

        if (properties.isRegister()) {
            RegistryServiceFactory registryServiceFactory = ExtensionLoaderFactory.load(RegistryServiceFactory.class, properties.getRegistryName());
            this.registryService = registryServiceFactory.connect(properties.getRegistryProperties());
        }
    }

    private Server(int port, String serializerName) {
        this.port = port;
        this.serverBootstrap = new ServerBootstrap();
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        SerializerFactory serializerFactory = ExtensionLoaderFactory.load(SerializerFactory.class, serializerName);
        Serializer serializer = serializerFactory.getInstance();
        this.serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new IdleStateHandler(0, 0, IDLE_TIMEOUT))
                                .addLast(new ProtocolDecoder())
                                .addLast(new ServerHandler(serializer))
                                .addLast(new ProtocolEncoder());
                    }
                });
    }

    public void start() throws Exception {
        serverBootstrap.bind(port).sync();
        logger.info("server listen in {}", port);
        if (properties != null && properties.isRegister()) {
            registryService.register(new ServerInfo(properties.getName(), "127.0.0.1", port, properties.getSerializerName()));
        }
    }

    public void shutdown() throws Exception {
        if(properties.isRegister()){
            registryService.destroy();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        logger.info("server:{} shutdown success", this);
    }


    /**
     * 注册服务
     *
     * @param clazz   service class
     * @param service service的实现类实例
     */
    public void registerService(Class<?> clazz, Object service) {
        ServiceLocator.INS.register(clazz, service);
    }

    @Override
    public String toString() {
        return "Server{" +
                "port=" + port +
                '}';
    }
}
