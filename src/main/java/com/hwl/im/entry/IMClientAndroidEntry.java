package com.hwl.im.entry;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    static Logger log = LogManager.getLogger(IMClientAndroidEntry.class.getName());
    static int CHECK_TIME_INTERNAL = 5; // s

    private final static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final static ScheduledExecutorService checkConnectExecutor = Executors.newSingleThreadScheduledExecutor();
    private final static ClientMessageOperate messageOperate = new ClientMessageOperate();
    private final static IMClientLauncher launcher = new IMClientLauncher("localhost", 8081);

    static {
        launcher.registerAction(messageOperate);
        launcher.registerClientListener(new AndroidClientListener());
    }

    private static void startCheckConnect() {
        if (checkConnectExecutor.isShutdown()) {
            checkConnectExecutor.schedule(new Runnable() {
                @Override
                public void run() {
                    connectServer();
                }
            }, CHECK_TIME_INTERNAL, TimeUnit.SECONDS);
        }
    }

    private static void stopCheckConnect() {
        checkConnectExecutor.shutdown();
    }

    public static void connectServer() {
        if (launcher.isConnected())
            return;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                launcher.connect();
            }
        });
    }

    public static void initListenExecutor() {
        messageOperate.registerListenExecutor(ImMessageType.ChatUser, new ChatUserMessageListen());
        messageOperate.registerListenExecutor(ImMessageType.ChatGroup, new ChatGroupMessageListen());
    }

    public static void sendUserValidateMessage(Long userId, String userToken) {
        messageOperate.send(new UserValidateSend(userId, userToken), new UserValidateListen((sessionid) -> {
            MessageRequestHeadOperate.setSessionid(sessionid);
            IMClientHeartbeatTimer.getInstance().run(new TimerTask() {
                @Override
                public void run() {
                    messageOperate.send(new HeartBeatMessageSend());
                }
            });
            return null;
        }, (msg) -> {
            launcher.stop();
            return null;
        }));
    }

    static class AndroidClientListener implements IMClientListener {

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
}