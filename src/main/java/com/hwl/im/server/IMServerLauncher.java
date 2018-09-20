package com.hwl.im.server;

import com.hwl.im.core.ImCoreConfig;
import com.hwl.im.core.imom.OnlineManage;
import com.hwl.im.core.imqos.RetryMessageManage;
import com.hwl.im.core.imstore.OfflineMessageManage;
import com.hwl.im.improto.ImMessageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class IMServerLauncher {
    static Logger log = LogManager.getLogger(IMServerLauncher.class.getName());

    private String host;
    private int port;

    private EventLoopGroup receiveGroup;
    private EventLoopGroup workGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;
    private IMServerLauncherConfig launcherConfig;

    public IMServerLauncher(String host, int port) {
        this.host = host;
        this.port = port;
        init();
    }

    public void setLauncherConfig(IMServerLauncherConfig launcherConfig) {
        if (launcherConfig == null) {
            this.launcherConfig = new IMServerLauncherConfig();
        } else {
            this.launcherConfig = launcherConfig;
        }
        this.initConfig();
    }

    private void init() {
        receiveGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();

        bootstrap = new ServerBootstrap();
        bootstrap.group(receiveGroup, workGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new IdleStateHandler(0, 0, ImCoreConfig.IDLE_TIMEOUT_SECONDS, TimeUnit.SECONDS));
                pipeline.addLast(new ProtobufVarint32FrameDecoder());
                pipeline.addLast(new ProtobufDecoder(ImMessageContext.getDefaultInstance()));
                pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast(new ProtobufEncoder());

                pipeline.addLast(new ServerMessageChannelHandler());
            }
        });

        // init server config
        this.initConfig();

        // retry
        RetryMessageManage.getInstance().startup();
    }

    public void bind() throws InterruptedException {
        log.debug("Server binding {}:{} ...", host, port);
        channelFuture = bootstrap.bind(host, port).sync();
        channelFuture.channel().closeFuture().sync();
    }

    public void stop() {
        if (receiveGroup != null) {
            receiveGroup.shutdownGracefully();
        }
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
    }

    private void initConfig() {
        if (this.launcherConfig != null) {
            if (this.launcherConfig.sessionStorageMedia != null) {
                OnlineManage.setSessionStorageMedia(this.launcherConfig.sessionStorageMedia);
            }

            if (this.launcherConfig.messageStorageMedia != null) {
                OfflineMessageManage.setOfflineMessageStorageMedia(this.launcherConfig.messageStorageMedia);
            }
        }
    }
}
