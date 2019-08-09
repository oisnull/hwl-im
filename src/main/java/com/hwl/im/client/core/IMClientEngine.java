package com.hwl.im.client.core;

import com.hwl.im.client.extra.IClientConnectListener;
import com.hwl.imcore.improto.ImMessageContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class IMClientEngine {
//    static Logger log = LogManager.getLogger(IMClientEngine.class.getName());

    public static final int STATUS_DISCONNECT = 0;
    public static final int STATUS_CONNECT = 1;

    private String host;
    private int port;
    private int status = STATUS_DISCONNECT;// 0:disconnect 1:connect

    private EventLoopGroup workGroup;
    private Bootstrap bootstrap;
    private Channel channel;
    private IClientConnectListener connectListener;
    private ClientMessageOperator messageOperator;

    public IMClientEngine(String host, int port) {
        this.host = host;
        this.port = port;

        init();
    }

    public void setConnectListener(IClientConnectListener connectListener) {
        this.connectListener = connectListener;
    }

    public void setMessageOperator(ClientMessageOperator messageOperator) {
        this.messageOperator = messageOperator;
        if (this.messageOperator == null) {
            throw new NullPointerException("ClientMessageOperator");
        }
    }

    private void init() {
        workGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(workGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new ProtobufVarint32FrameDecoder());
                pipeline.addLast(new ProtobufDecoder(ImMessageContext.getDefaultInstance()));
                pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast(new ProtobufEncoder());

                pipeline.addLast(new ClientMessageChannelHandler(messageOperator, connectListener));
            }
        });
    }

    public void connect() {
        if (status == STATUS_CONNECT)
            return;

        try {
            status = STATUS_CONNECT;
            channel = bootstrap.connect(host, port).sync().channel();
            messageOperator.setChannel(channel);
            this.connectListener.onBuildConnectionSuccess(channel.localAddress().toString(),
                    channel.remoteAddress().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.connectListener.onBuildConnectionFailure(channel.localAddress().toString(), e.getMessage());
            status = STATUS_DISCONNECT;
            stop();
        }
    }

    public void stop() {
        String localAddress = "";
        if (channel != null) {
            try {
                localAddress = channel.localAddress().toString();
                channel.close().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
        this.connectListener.onClosed(localAddress);
    }

    public boolean isConnected() {
        return status == STATUS_CONNECT;
    }

    public int getStatus() {
        return status;
    }

    public void resetStatus() {
        status = STATUS_DISCONNECT;
    }
}
