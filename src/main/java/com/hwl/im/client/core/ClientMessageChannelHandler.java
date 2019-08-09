package com.hwl.im.client.core;

import com.hwl.imcore.improto.ImMessageContext;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientMessageChannelHandler extends SimpleChannelInboundHandler<ImMessageContext> {

    private ClientMessageOperate messageOperate;
    private IClientChannelListener channelListener;

    public ClientMessageChannelHandler(ClientMessageOperate messageOperate, IClientChannelListener channelListener) {
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
        // log.debug("Client read0 : {}", msg.toString());
        this.messageOperate.listen(msg);
    }
}
