package com.hwl.im.client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.hwl.im.core.imaction.MessageListenExecutor;
import com.hwl.im.core.imaction.MessageSendExecutor;
import com.hwl.im.core.immode.MessageOperate;
import com.hwl.im.core.immode.MessageResponseHeadOperate;
import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageType;

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
    private Consumer<String> clientAckCallback = null;

    public void setClientAckCallback(Consumer<String> clientAckCallback) {
        this.clientAckCallback = clientAckCallback;
    }

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

    private void sendAckMessage(ImMessageContext messageContext) {
        if (this.clientAckCallback != null && MessageResponseHeadOperate.isAck(messageContext)) {
            this.clientAckCallback.accept(MessageResponseHeadOperate.getMessageId(messageContext));
        }
    }

    public void listen(ImMessageContext messageContext) {
        check();
        if (messageContext == null)
            return;

        this.sendAckMessage(messageContext);

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
}