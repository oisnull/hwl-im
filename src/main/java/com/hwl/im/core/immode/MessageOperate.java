package com.hwl.im.core.immode;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.hwl.im.core.ThreadPoolUtil;
import com.hwl.im.core.imaction.MessageSendExecutor;
import com.hwl.im.core.imom.OnlineManage;
import com.hwl.im.core.imqos.SentMessageManage;
import com.hwl.im.core.imsm.MonitorModel;
import com.hwl.im.core.imsm.ServerPushMonitorManage;
import com.hwl.im.core.imstore.OfflineMessageManage;
import com.hwl.imcore.improto.ImMessageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class MessageOperate {

    final static Logger log = LogManager.getLogger(MessageOperate.class.getName());
    final static ExecutorService executorService = Executors.newFixedThreadPool(ThreadPoolUtil.ioIntesivePoolSize());

    public static void send(Channel channel, ImMessageContext messageContext, Consumer<Boolean> callback) {
        if (channel == null || messageContext == null) {
            callback.accept(false);
            return;
        }
        ChannelFuture channelFuture = channel.writeAndFlush(messageContext);
        channelFuture.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (callback != null) {
                    log.debug("Server channel complete operate {}", future.isSuccess());
                    callback.accept(future.isSuccess());
                }
            }
        });
    }

    public static void clientSend(Channel channel, MessageSendExecutor sendExecutor) {
        if (sendExecutor == null || channel == null)
            return;
        ImMessageContext messageContext = sendExecutor.getMessageContext();
        if (messageContext == null) {
            sendExecutor.sendResultCallback(false);
            return;
        }
        ChannelFuture channelFuture = channel.writeAndFlush(messageContext);
        channelFuture.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (sendExecutor.isSendFailedAndClose() && !future.isSuccess()) {
                    channel.close();
                }
                sendExecutor.sendResultCallback(future.isSuccess());
            }
        });
    }

    public static void moveSentMessageIntoOffline(long userid) {
        LinkedList<ImMessageContext> messages = SentMessageManage.getInstance().getMessages(userid);
        if (messages == null) return;
        synchronized (messages) {
            OfflineMessageManage.getInstance().addMessages(userid, messages);
            messages.clear();
        }
    }

    public static void removeSentMessage(long userid, String messageGuid) {
        SentMessageManage.getInstance().removeMessage(userid, messageGuid);
        startExecutePush(userid);
    }

    private static void startExecutePush(long userid) {
        if (!ServerPushMonitorManage.getInstance().isRunning(userid)) {
            ServerPushMonitorManage.getInstance().start(userid);
            executorService.execute(() -> {
                serverPushOffline(userid, OfflineMessageManage.getInstance().pollMessage(userid));
            });
        }
    }

    /**
     * 客户端网络断开，服务器没有捕捉到，此时服务器认为可以连接到客户端
     * 服务器端仍然可以成功的推送数据到客户端，这段时间推送的数据都会储存在SentMessageManage中的Queue中
     * 当客户端没有重新连接直到服务器检测到，这时需要将SentMessageManage中Queue的数据转移到Offline离线存储库中
     * 当客户端重新连接并验证成功后，检测到SentMessageManage中的Queue有数据，
     * 这时需要将SentMessageManage中Queue的数据转移到Offline离线存储库中，并且开启离线消息推送线程
     */
    public static void serverPushOffline(long userid, String messageGuid) {
        SentMessageManage.getInstance().removeMessage(userid, messageGuid);
        moveSentMessageIntoOffline(userid);
        startExecutePush(userid);
    }

    public static void serverPushOffline(long userid, ImMessageContext messageContext) {
        if (userid <= 0 || messageContext == null) {
            ServerPushMonitorManage.getInstance().end(userid);
            MonitorModel model = ServerPushMonitorManage.getInstance().getModel(userid);
            log.debug("Server push offline message complete : userid({}) from {} to {} , count({}) , status({})",
                    userid, model.getStartExecTime(), model.getEndExecTime(), model.getMessageCount(), model.getStatus());
            return;
        }

        Channel toUserChannel = OnlineManage.getInstance().getChannel(userid);
        if (toUserChannel == null) {
            // offline
            log.debug("Server push offline message operate : user is offline , userid({}) message({})", userid, messageContext.toString());
            ServerPushMonitorManage.getInstance().endError(userid);
            OfflineMessageManage.getInstance().addMessage(userid, messageContext);
            return;
        }

        if (SentMessageManage.getInstance().isSpilled(userid)) {
            log.debug("Server push offline message operate : sent message queue is spilled , userid({}) message({})", userid, messageContext.toString());
            ServerPushMonitorManage.getInstance().endError(userid);
            OfflineMessageManage.getInstance().addMessage(userid, messageContext);
            return;
        }

        // online
        send(toUserChannel, messageContext, succ -> {
            if (succ) {
                ServerPushMonitorManage.getInstance().addCount(userid);
                SentMessageManage.getInstance().addMessage(userid, messageContext);
                serverPushOffline(userid, OfflineMessageManage.getInstance().pollMessage(userid));
            } else {
                // failed
                log.debug("Server push offline message failed : userid({}) message({})", userid, messageContext.toString());
                ServerPushMonitorManage.getInstance().endError(userid);
                OfflineMessageManage.getInstance().addMessage(userid, messageContext);
            }
        });
    }

    public static void serverPushOnline(Long userid, ImMessageContext messageContext, Consumer<Boolean> callback) {
        if (userid <= 0 || messageContext == null) return;

        Channel toUserChannel = OnlineManage.getInstance().getChannel(userid);
        if (toUserChannel == null || SentMessageManage.getInstance().isSpilled(userid)) {
            // add offline message
            OfflineMessageManage.getInstance().addMessage(userid, messageContext);
            return;
        }

        send(toUserChannel, messageContext, succ -> {
            if (callback != null) {
                callback.accept(succ);
            }
            if (succ) {
                SentMessageManage.getInstance().addMessage(userid, messageContext);
            } else {
                // failed
                OfflineMessageManage.getInstance().addMessage(userid, messageContext);
            }
        });
    }
}