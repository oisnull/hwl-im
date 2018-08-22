package com.hwl.im.core.immode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import com.hwl.im.core.ThreadPoolUtil;
import com.hwl.im.core.imom.OnlineManage;
import com.hwl.im.core.imqos.RetryMessageManage;
import com.hwl.im.core.imstore.OfflineMessageManage;
import com.hwl.im.core.proto.ImMessageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class MessageOperate {

    final static Logger log = LogManager.getLogger(MessageOperate.class.getName());
    final static ExecutorService executorService = Executors.newFixedThreadPool(ThreadPoolUtil.ioIntesivePoolSize());

    public static void send(Channel channel, ImMessageContext messageContext, Function<Boolean, Void> callback) {
        if (channel == null || messageContext == null)
            return;
        ChannelFuture channelFuture = channel.writeAndFlush(messageContext);
        channelFuture.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (callback != null)
                    callback.apply(future.isSuccess());
            }
        });
    }

    public static void serverSendAndRetry(Long userid, ImMessageContext messageContext,
            Function<Boolean, Void> callback) {
        Channel toUserChannel = OnlineManage.getInstance().getChannel(userid);
        if (toUserChannel == null) {
            // offline
            OfflineMessageManage.getInstance().addMessage(userid, messageContext);
            if (callback != null) {
                callback.apply(true);
            }
        } else {
            // online
            send(toUserChannel, messageContext, callback);
        }
    }

    public static void serverSendAndRetry(Long userid, ImMessageContext messageContext, Runnable succCallback) {
        Channel toUserChannel = OnlineManage.getInstance().getChannel(userid);
        if (toUserChannel == null) {
            // offline
            OfflineMessageManage.getInstance().addMessage(userid, messageContext);
        } else {
            // online
            send(toUserChannel, messageContext, new Function<Boolean, Void>() {

                @Override
                public Void apply(Boolean succ) {
                    if (succ) {
                        if (succCallback != null) {
                            succCallback.run();
                        }
                    } else {
                        // failed
                        RetryMessageManage.getInstance().addMessage(userid, messageContext);
                    }
                    return null;
                }
            });
        }
    }

    public static void serverPush(Long userid) {
        Channel toUserChannel = OnlineManage.getInstance().getChannel(userid);
        if (toUserChannel == null)
            return;

        executorService.execute(new Runnable() {

            @Override
            public void run() {
                log.debug("Server push offline message run , current thread {}", Thread.currentThread().getName());
                serverPushOfflineMessage(userid, toUserChannel);
            }
        });
    }

    private static void serverPushOfflineMessage(Long userid, Channel channel) {

        ImMessageContext messageContext = OfflineMessageManage.getInstance().pollMessage(userid);
        if (messageContext == null)
            return;

        send(channel, messageContext, new Function<Boolean, Void>() {

            @Override
            public Void apply(Boolean succ) {
                if (succ) {
                    log.debug("Sever push offline message success : {}", messageContext.toString());
                } else {
                    log.debug("Sever push offline message failed : {}", messageContext.toString());
                    // failed
                    RetryMessageManage.getInstance().addMessage(userid, messageContext);
                }
                serverPushOfflineMessage(userid, channel);
                return null;
            }
        });
    }

}