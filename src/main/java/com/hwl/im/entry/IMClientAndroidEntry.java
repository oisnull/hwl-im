package com.hwl.im.entry;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.hwl.im.client.ClientMessageOperate;
import com.hwl.im.client.IMClientHeartbeatTimer;
import com.hwl.im.client.IMClientLauncher;
import com.hwl.im.client.IMClientListener;
import com.hwl.im.client.listen.ChatGroupMessageListen;
import com.hwl.im.client.listen.ChatUserMessageListen;
import com.hwl.im.client.listen.UserValidateListen;
import com.hwl.im.client.send.HeartBeatMessageSend;
import com.hwl.im.client.send.UserValidateSend;
import com.hwl.im.core.immode.MessageRequestHeadOperate;
import com.hwl.im.core.proto.ImMessageType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IMClientAndroidEntry {

    /**
     * Function list :
     * 
     * 
     */

    static Logger log = LogManager.getLogger(IMClientAndroidEntry.class.getName());
    static int CHECK_TIME_INTERNAL = 5; // s

    final static ExecutorService executorService = Executors.newSingleThreadExecutor();
    final static ScheduledExecutorService checkConnectExecutor = Executors.newSingleThreadScheduledExecutor();
    final static ClientMessageOperate messageOperate = new ClientMessageOperate();
    final static IMClientLauncher launcher = new IMClientLauncher("localhost", 8081);

    static {
        initListenExecutor();
        launcher.registerAction(messageOperate);
        launcher.registerClientListener(new AndroidClientListener());
    }

    static void initListenExecutor() {
        messageOperate.registerListenExecutor(ImMessageType.ChatUser, new ChatUserMessageListen());
        messageOperate.registerListenExecutor(ImMessageType.ChatGroup, new ChatGroupMessageListen());
    }

    static void startCheckConnect() {
        if (checkConnectExecutor.isShutdown()) {
            checkConnectExecutor.schedule(new Runnable() {
                @Override
                public void run() {
                    connectServer();
                }
            }, CHECK_TIME_INTERNAL, TimeUnit.SECONDS);
        }
    }

    static void stopCheckConnect() {
        if (checkConnectExecutor.isShutdown())
            return;
        checkConnectExecutor.shutdown();
        // stopHeartbeat();
    }

    public static void connectServer() {
        if (launcher.isConnected()) {
            stopCheckConnect();
            return;
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                launcher.connect();
            }
        });
    }

    static void commomExec(DefaultSendOperateListener operateListener, Consumer<Consumer<Boolean>> sendConsumer) {
        if (!launcher.isConnected()) {
            operateListener.unconnect();
            return;
        }
        Consumer<Boolean> sendCallback = new Consumer<Boolean>() {

            @Override
            public void accept(Boolean succ) {
                if (!succ) {
                    operateListener.notSendToServer();
                }
            }
        };

        sendConsumer.accept(sendCallback);
    }

    public static void sendUserValidateMessage(Long userId, String userToken,
            DefaultSendOperateListener operateListener) {

        commomExec(operateListener, (sendCallback) -> {

            UserValidateSend request = new UserValidateSend(userId, userToken, sendCallback);
            UserValidateListen response = new UserValidateListen(sess -> {
                operateListener.success();
                startHeartbeat(sess);
            }, msg -> {
                operateListener.failed(msg);
                stopHeartbeat();
            });
            messageOperate.send(request, response);

        });

        // if (!launcher.isConnected()) {
        // operateListener.unconnect();
        // return;
        // }
        // Consumer<Boolean> sendCallback = new Consumer<Boolean>() {

        // @Override
        // public void accept(Boolean succ) {
        // if (!succ) {
        // operateListener.notSendToServer();
        // }
        // }
        // };
        // UserValidateSend request = new UserValidateSend(userId, userToken,
        // sendCallback);
        // UserValidateListen response = new UserValidateListen(sess -> {
        // operateListener.success();
        // startHeartbeat(sess);
        // }, msg -> {
        // operateListener.failed(msg);
        // stopHeartbeat();
        // });
        // messageOperate.send(request, response);
    }

    private static void startHeartbeat(String sessionId) {
        MessageRequestHeadOperate.setSessionid(sessionId);
        IMClientHeartbeatTimer.getInstance().run(new TimerTask() {
            @Override
            public void run() {
                messageOperate.send(new HeartBeatMessageSend());
            }
        });
    }

    private static void stopHeartbeat() {
        IMClientHeartbeatTimer.getInstance().stop();
        launcher.stop();
    }

    public static class DefaultSendOperateListener {

        public void unconnect() {
            log.debug("Android client send operate : unconnect server ");
        }

        public void notSendToServer() {
            log.debug("Android client send operate : message not send to server ");
        }

        public void success() {

        }

        public void failed(String message) {
            log.debug("Android client send operate failed : {}", message);
        }

        public void sessionidInvaild() {
            log.debug("Android client send operate : sessionid invalid");
        }

    }

   private static class AndroidClientListener implements IMClientListener {

        @Override
        public void onBuildConnectionSuccess(String clientAddress, String serverAddress) {
            log.debug("Client {} connected to server {} successfully.", clientAddress, serverAddress);
            stopCheckConnect();
        }

        @Override
        public void onBuildConnectionError(String clientAddress, String serverAddress, String errorInfo) {
            log.error("Client {} connected to server {} failure. info :", clientAddress, serverAddress, errorInfo);
            startCheckConnect();
        }

        @Override
        public void onClosed(String clientAddress) {
            log.debug("Client {} closed", clientAddress);
            startCheckConnect();
        }

        @Override
        public void onDisconnected(String clientAddress) {
            log.debug("Client {} disconnect", clientAddress);
            startCheckConnect();
        }

        @Override
        public void onError(String clientAddress, String errorInfo) {
            log.error("An error occurred on the client {}. info : {}", clientAddress, errorInfo);
            startCheckConnect();
        }

    }

    public static void main(String[] args) {
        IMClientAndroidEntry.connectServer();
        IMClientAndroidEntry.sendUserValidateMessage(10000L, "123456", new DefaultSendOperateListener() {
            @Override
            public void success() {
                
            }
        });
    }
}