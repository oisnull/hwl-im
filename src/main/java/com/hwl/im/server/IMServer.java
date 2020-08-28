package com.hwl.im.server;

import com.hwl.im.server.action.OnlineChannelManager;
import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.IMServerEngine;
import com.hwl.im.server.core.ServerMessageExecuteFactory;
import com.hwl.im.server.core.ServerMessageReceiveExecutor;
import com.hwl.im.server.defa.DefaultOfflineMessageManager;
import com.hwl.im.server.defa.DefaultOnlineSessionManager;
import com.hwl.im.server.defa.DefaultRequestValidator;
import com.hwl.im.server.extra.IOfflineMessageStorage;
import com.hwl.im.server.extra.IOnlineSessionStorage;
import com.hwl.im.server.extra.IRequestValidator;
import com.hwl.im.server.receive.*;
import com.hwl.imcore.improto.ImMessageRequest;
import com.hwl.imcore.improto.ImMessageType;

import java.util.function.Function;

public class IMServer {
    public String host;
    public int port;
    private IOfflineMessageStorage _messageStorage;
    private IOnlineSessionStorage _sessionStorage;
    private IRequestValidator _requestValidator;
    private IMServerEngine serverEngine;

    public IMServer(String host, int port) {
        this.host = host;
        this.port = port;

        _messageStorage = new DefaultOfflineMessageManager("offline");
        _sessionStorage = new DefaultOnlineSessionManager();
        _requestValidator = new DefaultRequestValidator();
    }

    public void setOfflineMessageStorage(IOfflineMessageStorage messageStorage) {
        if (messageStorage == null) return;

        _messageStorage = messageStorage;
    }

    public void setOnlineSessionStorage(IOnlineSessionStorage sessionStorage) {
        if (sessionStorage == null) return;

        _sessionStorage = sessionStorage;
    }

    public void setRequestValidator(IRequestValidator requestValidator) {
        if (requestValidator == null) return;

        _requestValidator = requestValidator;
    }

    public void registerReceiveExecutor(ImMessageType messageType, Function<ImMessageRequest, ServerMessageReceiveExecutor> executorFunction) {
        if (executorFunction == null)
            throw new NullPointerException("The param of ServerMessageReceiveExecutor is empty.");

        //if (MessageExecuteFactory.isExists(messageType))
        //    throw new Exception("Duplicate message types");

        ServerMessageExecuteFactory.registerReceiveExecutor(messageType, executorFunction);
    }

    public void start() {
        serverEngine = new IMServerEngine(this.host, this.port);
        serverEngine.setRequestValidator(this._requestValidator);

        OnlineChannelManager.setSessionStorage(this._sessionStorage);
        ServerMessageOperator.setOfflineMessageStorage(this._messageStorage);

        this.initDefaultReceiveExecutor();

        try {
            serverEngine.bind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDefaultReceiveExecutor() {
        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.UserValidate,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {

                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new UserValidateReceiveExecutor(t.getUserValidateRequest());
                    }
                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.HeartBeat,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {

                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new HeartBeatMessageReceiveExecutor(t.getHeartBeatMessageRequest());
                    }
                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.ChatUser,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {

                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new ChatUserMessageReceiveExecutor(t.getChatUserMessageRequest());
                    }
                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.ChatGroup,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {

                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new ChatGroupMessageReceiveExecutor(t.getChatGroupMessageRequest());
                    }
                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.AddFriend,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {

                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new AddFriendMessageReceiveExecutor(t.getAddFriendMessageRequest());
                    }
                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.TestConnection,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {
                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new TestConnectionMessageReceiveExecutor(t.getTestConnectionMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.ClientAckMessage,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {
                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new AckMessageReceiveExecutor(t.getAckMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.ChatSetting,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {
                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new ChatSettingMessageReceiveExecutor(t.getChatSettingMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.GroupOperate,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {
                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new GroupOperateMessageReceiveExecutor(t.getGroupOperateMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.NearCircleOperate,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {
                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new NearCircleOperateMessageReceiveExecutor(t.getNearCircleOperateMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.CircleOperate,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {
                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new CircleOperateMessageReceiveExecutor(t.getCircleOperateMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.SystemMessage,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {
                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new SystemMessageReceiveExecutor(t.getSystemMessageRequest());
                    }

                });
                
        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.AppVersion,
                new Function<ImMessageRequest, ServerMessageReceiveExecutor>() {
                    @Override
                    public ServerMessageReceiveExecutor apply(ImMessageRequest t) {
                        return new AppVersionMessageReceiveExecutor(t.getAppVersionRequest());
                    }
                });
    }
}