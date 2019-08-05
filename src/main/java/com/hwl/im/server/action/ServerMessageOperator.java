package com.hwl.im.server.action;

import com.hwl.im.core.ThreadPoolUtil;
import com.hwl.im.server.extra.IOfflineMessageStorage;
import com.hwl.imcore.improto.ImMessageContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMessageOperator {

    private final static Logger log = LogManager.getLogger(ServerMessageOperator.class.getName());
    private final static ExecutorService executorService = Executors.newFixedThreadPool(ThreadPoolUtil.ioIntesivePoolSize());
    private static ServerMessageOperator instance = new ServerMessageOperator();

    private ServerSentMessageManager sentMessageManager;
    private IOfflineMessageStorage offlineMessageManager;
    private ServerPushMonitor pushMonitor;

    private ServerMessageOperator() {
        sentMessageManager = new ServerSentMessageManager("temp");
        pushMonitor = new ServerPushMonitor();
    }

    public static ServerMessageOperator getInstance() {
        if (instance == null)
            instance = new ServerMessageOperator();

        return instance;
    }

    public static void setOfflineMessageStorage(IOfflineMessageStorage messageStorage) {
        instance.offlineMessageManager = messageStorage;
        if (instance.offlineMessageManager == null) {
            throw new NullPointerException("The param of offlineMessageManager is empty.");
        }
    }

    public static boolean isAck(ImMessageContext messageContext) {
        if (messageContext != null && messageContext.getResponse() != null && messageContext.getResponse().getResponseHead() != null) {
            return messageContext.getResponse().getResponseHead().getIsack();
        }
        return false;
    }

    public static String getMessageId(ImMessageContext messageContext) {
        if (messageContext != null && messageContext.getResponse() != null && messageContext.getResponse().getResponseHead() != null) {
            return messageContext.getResponse().getResponseHead().getMessageid();
        }
        return null;
    }

    public void push(Channel channel, ImMessageContext messageContext) {
        push(0, channel, messageContext, false);
    }

    public void push(long toUserId, Channel channel, ImMessageContext messageContext, boolean isOfflineMessage) {
        if (channel == null || messageContext == null) return;

        ChannelFuture channelFuture = channel.writeAndFlush(messageContext);
        channelFuture.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    String successDesc = String.format("Server push to userid({}) success", toUserId);
                    if (toUserId > 0 && isAck(messageContext)) {
                        successDesc += "," + getMessageId(messageContext);
                        //add temp message container
                        sentMessageManager.addMessage(toUserId, messageContext);
                    }
                    if (toUserId > 0)
                        log.debug(successDesc);
                } else {
                    if (toUserId > 0) {
                        String failedDesc = String.format("Server push to userid({}) failed", toUserId);
                        if (isOfflineMessage) {
                            failedDesc += ",addaddFirst," + getMessageId(messageContext);
                            offlineMessageManager.addFirst(toUserId, messageContext);
                        } else {
                            failedDesc += ",addMessage," + getMessageId(messageContext);
                            offlineMessageManager.addMessage(toUserId, messageContext);
                        }

                        log.debug(failedDesc);
                    }
                }
            }
        });
    }

    public void push(long toUserId, ImMessageContext messageContext, boolean isOfflineMessage) {
        if (toUserId <= 0 || messageContext == null) {
            return;
        }

        Channel toUserChannel = OnlineChannelManager.getInstance().getChannel(toUserId);
        if (toUserChannel == null) {
            //User is offline
            offlineMessageManager.addMessage(toUserId, messageContext);
            return;
        }

        push(toUserId, toUserChannel, messageContext, isOfflineMessage);
    }

    private void loopPushOfflineMessage(long toUserId, Channel channel) {
        if (channel == null) {
            channel = OnlineChannelManager.getInstance().getChannel(toUserId);
        }

        if (channel == null || !channel.isOpen() || !channel.isActive()) {
            return;
        }

        //check exists offline message
        ImMessageContext messageContext = offlineMessageManager.pollMessage(toUserId);
        if (messageContext == null) {
            return;
        }

        push(toUserId, channel, messageContext, true);
        pushMonitor.addCount(toUserId);

        loopPushOfflineMessage(toUserId, channel);
    }

    public void startPush(long userId) {
        if (pushMonitor.isRunning(userId)) return;

        log.debug("Server start push to userid({}) ...", userId);
        pushMonitor.start(userId);
        executorService.execute(() -> {
            loopPushOfflineMessage(userId, null);
            pushMonitor.removeStatus(userId);
            log.debug("Server push to userid({}) end.", userId);
        });
    }

    public void deleteSentMessage(long userId, String messageGuid) {
        log.debug("Server delete sent message of userid({}) and messageGuid({}) ...", userId, messageGuid);
        sentMessageManager.removeMessage(userId, messageGuid);
    }

    public void moveSentMessageIntoOffline(Channel channel) {
        long userId = OnlineChannelManager.getInstance().getUserId(channel);
        if (userId <= 0) return;

        List<ImMessageContext> messages = sentMessageManager.getMessages(userId);
        if (messages == null || messages.size() <= 0) return;

        log.debug("Server move sent message of userid({}) into offline store.", userId);
        synchronized (messages) {
            offlineMessageManager.addMessages(userId, messages);
            messages.clear();
            sentMessageManager.removeUser(userId);
        }
    }
}