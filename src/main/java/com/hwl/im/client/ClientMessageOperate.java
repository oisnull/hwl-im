package com.hwl.im.client;

import java.util.concurrent.ConcurrentHashMap;

import com.hwl.im.core.imaction.MessageListenExecutor;
import com.hwl.im.core.imaction.MessageSendExecutor;
import com.hwl.im.core.immode.MessageOperate;
import com.hwl.im.core.proto.ImMessageContext;
import com.hwl.im.core.proto.ImMessageType;

import io.netty.channel.Channel;

public final class ClientMessageOperate {

    private void check() {
        if (this.serverChannel == null) {
            throw new NullPointerException("连接的服务器通道不存在,请尝试调用 IMClientLauncher 中的 connect() 后再试.");
        }
    }

    private Channel serverChannel = null;
    private ConcurrentHashMap<ImMessageType, MessageListenExecutor> listenExecutors = new ConcurrentHashMap<>();
    private Runnable disconnectCallback = null;

    public void registerChannel(Channel channel) {
        this.serverChannel = channel;
    }

    public void unregisterChannel() {
        this.serverChannel = null;
    }

    public void registerDisconnectCallback(Runnable disconnectCallback) {
        this.disconnectCallback = disconnectCallback;
    }

    public void registerListenExecutor(ImMessageType messageType, MessageListenExecutor executor) {
        if (this.listenExecutors.containsKey(messageType)) {
            this.listenExecutors.remove(messageType);
        }
        this.listenExecutors.put(messageType, executor);
    }

    public void unregisterListenExecutor(ImMessageType messageType) {
        if (this.listenExecutors.containsKey(messageType)) {
            this.listenExecutors.remove(messageType);
        }
    }

    public void send(MessageSendExecutor sendExecutor) {
        check();
        MessageOperate.clientSend(serverChannel, sendExecutor);
    }

    public void send(MessageSendExecutor sendExecutor, MessageListenExecutor listenExecutor) {
        if (sendExecutor == null)
            return;
        if (listenExecutor != null) {
            registerListenExecutor(sendExecutor.getMessageType(), listenExecutor);
        }
        this.send(sendExecutor);
    }

    public void listen(ImMessageContext messageContext) {
        check();
        if (messageContext == null)
            return;
        MessageListenExecutor listenExecutor = listenExecutors.get(messageContext.getType());
        if (listenExecutor == null)
            return;

        listenExecutor.execute(messageContext);
        if (listenExecutor.executedAndClose() && serverChannel != null) {
            serverChannel.close();
        }
    }

    public void disconnect() {
        if (this.disconnectCallback != null) {
            this.disconnectCallback.run();
        }
        unregisterChannel();
    }

    // public void send(MessageSendExecutor sendExecutor, MessageListenExecutor
    // listenExecutor) {
    // check();
    // if (sendExecutor == null)
    // return;
    // ChannelFuture channelFuture =
    // this.serverChannel.writeAndFlush(sendExecutor.getMessageContext());
    // channelFuture.addListener(new ChannelFutureListener() {

    // @Override
    // public void operationComplete(ChannelFuture future) throws Exception {
    // if (sendExecutor.isSendFailedAndClose() && !future.isSuccess()) {
    // serverChannel.close();
    // unregisterChannel();
    // }
    // sendExecutor.sendResultCallback(future.isSuccess());
    // }
    // });
    // }

    // public void read(ImMessageContext messageContext) {
    // if (messageContext == null || this.listenExecutors.size() <= 0)
    // return;

    // MessageListenExecutor executor =
    // this.listenExecutors.get(messageContext.getType());
    // if (executor == null)
    // return;
    // executor.execute(messageContext);
    // if (executor.executedAndClose() && this.serverChannel != null) {
    // this.serverChannel.close();
    // unregisterChannel();
    // }
    // }
}