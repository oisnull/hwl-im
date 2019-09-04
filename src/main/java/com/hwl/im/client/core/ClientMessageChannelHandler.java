package com.hwl.im.client.core;

import com.hwl.im.client.extra.IClientChannelListener;
import com.hwl.imcore.improto.ImMessageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientMessageChannelHandler extends SimpleChannelInboundHandler<ImMessageContext> {

    static Logger log = LogManager.getLogger(ClientMessageChannelHandler.class.getName());

    private ClientMessageOperator messageOperate;
    private IClientChannelListener channelListener;

    public ClientMessageChannelHandler(ClientMessageOperator messageOperate, IClientChannelListener channelListener) {
        this.messageOperate = messageOperate;
        this.channelListener = channelListener;
    }

    // @Override
    // public void channelActive(ChannelHandlerContext ctx) throws Exception {
    // log.debug("Client active : {}", ctx.channel().remoteAddress());
    // }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // log.debug("Client inactive : {}", ctx.channel().remoteAddress());
        this.channelListener.onDisconnected(ctx.channel().localAddress().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // log.error("Client passive disconnect : {}", ctx.channel().remoteAddress());
        String clientAddress = ctx.channel().localAddress().toString();
        cause.printStackTrace();
        ctx.close();
        this.channelListener.onError(clientAddress, cause.getMessage());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ImMessageContext msg) throws Exception {
        // if (isDebug)
        log.debug("Client read0 : {}", msg.toString());
        this.messageOperate.listen(msg);
    }
}
