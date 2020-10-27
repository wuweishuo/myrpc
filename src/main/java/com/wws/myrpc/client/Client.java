package com.wws.myrpc.client;

import com.wws.myrpc.client.callback.CallbackContext;
import com.wws.myrpc.client.callback.CallbackFuture;
import com.wws.myrpc.client.callback.CallbackPool;
import com.wws.myrpc.client.handler.ServiceReturnHandler;
import com.wws.myrpc.core.handler.ProtocolDecoder;
import com.wws.myrpc.core.handler.ProtocolEncoder;
import com.wws.myrpc.core.protocol.Header;
import com.wws.myrpc.core.protocol.Protocol;
import com.wws.myrpc.serialization.JdkSerializer;
import com.wws.myrpc.serialization.Serializer;
import com.wws.myrpc.server.handler.ServiceInvokeHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Client {

    private String ip;

    private int port;

    private Bootstrap bootstrap;
    private NioEventLoopGroup workerGroup;

    private Serializer serializer = new JdkSerializer();

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
                        socketChannel.pipeline().addLast(new ProtocolDecoder());
                        socketChannel.pipeline().addLast(new ServiceReturnHandler());
                        socketChannel.pipeline().addLast(new ProtocolEncoder());
                    }
                });
    }

    public Channel connect() throws InterruptedException {
        ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
        System.out.println("client connect to "+ ip +":" + port);
        return channelFuture.channel();

    }

    public <T> T transport(Method method, Object[] args) throws InterruptedException, ExecutionException, IOException {
        Channel channel = this.connect();
        long flowId = doTransport(channel, method, args);
        CallbackFuture<T> callbackFuture = new CallbackFuture<>();
        CallbackPool.INS.put(flowId, new CallbackContext(callbackFuture, method.getGenericReturnType()));
        return callbackFuture.get();
    }

    private long doTransport(Channel channel, Method method, Object[] args) throws IOException {
        Type[] parameterTypes = method.getGenericParameterTypes();
        byte[] bytes = serializer.serialize(parameterTypes, args);
        Header header = new Header();
        header.setBodyLen(bytes.length);
        header.setMethod(method.toGenericString());
        header.setFlowId(1L);
        header.setAid(1L);
        Protocol protocol = new Protocol();
        protocol.setHeader(header);
        protocol.setBody(bytes);
        channel.writeAndFlush(protocol);
        return 1L;
    }


}
