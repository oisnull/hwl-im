package com.hwl.im.client.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageType;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public final class ClientMessageOperator {
    private Channel serverChannel = null;
    private ConcurrentHashMap<ImMessageType, IClientMessageListenExecutor> listenExecutors = new ConcurrentHashMap<>();
    private Consumer<String> clientAckSender = null;

    public void setClientAckSender(Consumer<String> clientAckSender) {
        this.clientAckSender = clientAckSender;
    }

    public void setChannel(Channel channel) {
        this.serverChannel = channel;
    }

    public void registerListenExecutor(ImMessageType messageType, IClientMessageListenExecutor executor) {
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

    public void send(AbstractMessageSendExecutor sendExecutor) {
        this.send(sendExecutor, null);
    }

    public void send(AbstractMessageSendExecutor sendExecutor, IClientMessageListenExecutor listenExecutor) {
        if (sendExecutor == null) return;

        if (listenExecutor != null) {
            registerListenExecutor(sendExecutor.getMessageType(), listenExecutor);
        }

        ChannelFuture channelFuture = serverChannel.writeAndFlush(sendExecutor.getMessageContext());
        channelFuture.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    sendExecutor.success();
                } else {
                    sendExecutor.failure("server channel error.");
                }
            }
        });
    }

    public void listen(ImMessageContext messageContext) {
        if (messageContext == null) return;

        if (this.clientAckSender != null && messageContext.getResponse().getResponseHead().getIsack()) {
            this.clientAckSender.accept(messageContext.getResponse().getResponseHead().getMessageid());
        }

        IClientMessageListenExecutor listenExecutor = listenExecutors.get(messageContext.getType());
        if (listenExecutor != null) {
            listenExecutor.execute(messageContext);
            if (listenExecutor.executedAndClose()) {
                serverChannel.close();
            }
        }
    }
}