package com.hwl.im.entry;

import java.util.function.Function;

import com.hwl.im.core.imaction.MessageReceiveExecutor;
import com.hwl.imcore.improto.ImMessageRequest;
import com.hwl.imcore.improto.ImMessageType;
import com.hwl.im.server.IMServerLauncher;
import com.hwl.im.server.IMServerLauncherConfig;
import com.hwl.im.server.receive.*;
import com.hwl.im.server.redis.OfflineMessageStorage;
import com.hwl.im.server.redis.SessionStorage;
import com.hwl.imcore.improto.ImTestConnectionMessageRequest;

public class IMServerEntry {

    public final static String SERVER_FORMAT_PREFIX = "imserver";
    public final static String SERVER_FORMAT_DESCRIPTION = "imserver localhost port";
    public final static int SERVER_FORMAT_LENGTH = 3;

    private String[] commandStr;
    private boolean isValid = true;
    private String invalidMessage = null;

    private String host;
    private int port;

    public IMServerEntry(String[] commandStr) {
        this.commandStr = commandStr;

        check();
    }

    private void check() {
        if (commandStr == null || commandStr.length < SERVER_FORMAT_LENGTH) {
            isValid = false;
            invalidMessage = "command string format error";
            return;
        }
        if (commandStr[0].equals(SERVER_FORMAT_PREFIX)) {
            host = commandStr[1];
            port = Integer.parseInt(commandStr[2]);
            isValid = true;
            invalidMessage = null;
        } else {
            isValid = false;
            invalidMessage = "command string parse error";
            return;
        }
    }

    public boolean commandIsValid() {
        return this.isValid;
    }

    public String getInvalidMessage() {
        return this.invalidMessage;
    }

    public void bind() throws InterruptedException {
        if (!isValid)
            return;

        IMServerLauncherConfig config = new IMServerLauncherConfig();
        config.sessionStorageMedia = new SessionStorage();
        config.messageStorageMedia = new OfflineMessageStorage();
        initReceiveExecuteConfig(config);

        IMServerLauncher serverLauncher = new IMServerLauncher(host, port);
        serverLauncher.setLauncherConfig(config);
        serverLauncher.bind();
    }

    private void initReceiveExecuteConfig(IMServerLauncherConfig config) {
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

        config.registerReceiveExecutor(ImMessageType.AddFriend,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {

                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new AddFriendMessageReceiveExecutor(t.getAddFriendMessageRequest());
                    }
                });

        config.registerReceiveExecutor(ImMessageType.TestConnection,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {
                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new TestConnectionMessageReceiveExecutor(t.getTestConnectionMessageRequest());
                    }

                });
    }
}