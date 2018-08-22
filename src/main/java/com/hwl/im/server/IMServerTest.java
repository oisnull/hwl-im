package com.hwl.im.server;

import java.util.function.Function;

import com.hwl.im.core.imaction.MessageReceiveExecutor;
import com.hwl.im.core.proto.ImMessageRequest;
import com.hwl.im.core.proto.ImMessageType;
import com.hwl.im.server.receive.ChatGroupMessageReceiveExecutor;
import com.hwl.im.server.receive.ChatUserMessageReceiveExecutor;
import com.hwl.im.server.receive.HeartBeatMessageReceiveExecutor;
import com.hwl.im.server.receive.UserValidateReceiveExecutor;
import com.hwl.im.server.redis.OfflineMessageStorage;
import com.hwl.im.server.redis.SessionStorage;

public class IMServerTest {

    public static void main(String[] args) throws InterruptedException {
        IMServerLauncherConfig config = new IMServerLauncherConfig();
        config.sessionStorageMedia = new SessionStorage();
        config.messageStorageMedia= new OfflineMessageStorage();
        initReceiveExecuteConfig(config);

        IMServerLauncher serverLauncher = new IMServerLauncher("127.0.0.1", 8081);
        serverLauncher.setLauncherConfig(config);
        serverLauncher.bind();
    }

    public static void initReceiveExecuteConfig(IMServerLauncherConfig config) {
        config.registerReceiveExecutor(ImMessageType.UserValidate,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {

                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new UserValidateReceiveExecutor(t.getUserValidateRequest());
                    }
                });

                config.registerReceiveExecutor(ImMessageType.HeartBeat,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {

                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new HeartBeatMessageReceiveExecutor(t.getHeartBeatMessageRequest());
                    }
                });

                config.registerReceiveExecutor(ImMessageType.ChatUser,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {

                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new ChatUserMessageReceiveExecutor(t.getChatUserMessageRequest());
                    }
                });

                config.registerReceiveExecutor(ImMessageType.ChatGroup,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {

                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new ChatGroupMessageReceiveExecutor(t.getChatGroupMessageRequest());
                    }
                });
    }
}