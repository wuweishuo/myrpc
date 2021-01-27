package com.wws.myrpc.client.instance;

import com.wws.myrpc.client.Client;
import com.wws.myrpc.client.instance.callback.CallbackContext;
import com.wws.myrpc.client.instance.callback.CallbackContextMap;
import com.wws.myrpc.client.instance.callback.CallbackFuture;
import com.wws.myrpc.client.instance.constance.AttributeKeyConst;
import com.wws.myrpc.client.instance.handler.ClientHandler;
import com.wws.myrpc.core.exception.RpcException;
import com.wws.myrpc.core.handler.ProtocolDecoder;
import com.wws.myrpc.core.handler.ProtocolEncoder;
import com.wws.myrpc.core.protocol.Header;
import com.wws.myrpc.core.protocol.Protocol;
import com.wws.myrpc.core.protocol.Request;
import com.wws.myrpc.serialize.Serializer;
import com.wws.myrpc.spi.ExtensionLoaderFactory;
import com.wws.myrpc.util.impl.UUIdGenerator;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

/**
 * SimpleClient
 * ip+port客户端
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class SimpleClient implements Client {

    private final String ip;

    private final int port;

    private final Bootstrap bootstrap;
    private final NioEventLoopGroup workerGroup;

    private final int HEART_BEAT = 1;

    private final Serializer serializer;

    public SimpleClient(String ip, int port){
        this(ip, port, "jdk");
    }

    public SimpleClient(SimpleClientProperties properties) {
        this(properties.getIp(), properties.getPort(), properties.getSerializerName());
    }

    public SimpleClient(String ip, int port, String serializerName) {
        this.ip = ip;
        this.port = port;
        this.serializer = ExtensionLoaderFactory.load(Serializer.class, serializerName);

        this.bootstrap = new Bootstrap();
        this.workerGroup = new NioEventLoopGroup();
        this.bootstrap.channel(NioSocketChannel.class)
                .group(workerGroup)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new IdleStateHandler(0, HEART_BEAT, 0))
                                .addLast(new ProtocolDecoder())
                                .addLast(new ClientHandler(serializer))
                                .addLast(new ProtocolEncoder());
                    }
                });
    }

    public Channel connect() throws InterruptedException {
        ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
        Channel channel = channelFuture.channel();
        channel.attr(AttributeKeyConst.CALLBACK_CONTEXT_MAP_ATTRIBUTE_KEY).set(new CallbackContextMap());
        channel.attr(AttributeKeyConst.ID_GENERATOR_ATTRIBUTE_KEY).set(new UUIdGenerator());
        return channel;
    }

    public void shutdown() {
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    public <T> T transport(Method method, Class<T> returnType, Object... args) throws Throwable {
        Channel channel = this.connect();

        Request request = new Request();
        request.setMethod(method.toGenericString());
        request.setArgs(args);
        long flowId = doTransport(channel, request);

        CallbackFuture<T> callbackFuture = new CallbackFuture<>();
        CallbackContextMap callbackContextMap = channel.attr(AttributeKeyConst.CALLBACK_CONTEXT_MAP_ATTRIBUTE_KEY).get();
        callbackContextMap.put(flowId, new CallbackContext(callbackFuture, returnType));
        try {
            return callbackFuture.get();
        } catch (ExecutionException e) {
            throw new RpcException(e.getCause());
        }

    }

    private long doTransport(Channel channel, Request request) throws IOException {
        byte[] bytes = serializer.serialize(request);
        Header header = new Header();
        long flowId = channel.attr(AttributeKeyConst.ID_GENERATOR_ATTRIBUTE_KEY).get().generate();
        header.setFlowId(flowId);
        header.setBodyLen(bytes.length);
        Protocol protocol = new Protocol();
        protocol.setHeader(header);
        protocol.setBody(bytes);
        channel.writeAndFlush(protocol);
        return flowId;
    }

    @Override
    public String toString() {
        return "SimpleClient{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
