package com.hwl.im.client;

import com.hwl.im.improto.ImMessageContext;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientMessageChannelHandler extends SimpleChannelInboundHandler<ImMessageContext> {

    // static boolean isDebug = false;
    // private static Logger log =
    // LogManager.getLogger(ClientMessageChannelHandler.class.getName());
    private ClientMessageOperate messageOperate;
    private IMClientListener clientListener;

    public ClientMessageChannelHandler(ClientMessageOperate messageOperate, IMClientListener clientListener) {
        this.messageOperate = messageOperate;
        this.clientListener = clientListener;
        if (this.messageOperate == null) {
            throw new NullPointerException("ClientMessageOperate");
        }
    }

    // @Override
    // public void channelActive(ChannelHandlerContext ctx) throws Exception {
    // log.debug("Client active : {}", ctx.channel().remoteAddress());
    // }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // log.debug("Client inactive : {}", ctx.channel().remoteAddress());
        this.clientListener.onDisconnected(ctx.channel().localAddress().toString());
        this.messageOperate.disconnect();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // log.error("Client passive disconnect : {}", ctx.channel().remoteAddress());
        String clientAddress = ctx.channel().localAddress().toString();
        cause.printStackTrace();
        ctx.close();
        this.clientListener.onError(clientAddress, cause.getMessage());
        this.clientListener.onDisconnected(clientAddress);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ImMessageContext msg) throws Exception {
        if (msg == null)
            return;
        // if (isDebug)
        // log.debug("Client read0 : {}", msg.toString());
        this.messageOperate.listen(msg);
    }
}
