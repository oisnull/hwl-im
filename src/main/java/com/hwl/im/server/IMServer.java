package com.hwl.im.server;

public class IMServer {
    public String host;
    public int port ;
    private IOfflineMessageStorage _messageStorage;
    private IOnlineSessionStorage _sessionStorage;
    private IRequestValidator _requestValidator;
    private IMServerEngine serverEngine;

    public IMServer(String host, int port)
    {
        this.host = ip;
        this.port = port;

        _messageStorage = new DefaultOfflineMessageManager("offline");
        _sessionStorage = new DefaultOnlineSessionManager();
        _requestValidator = new DefaultRequestValidator();
    }

    public void setOfflineMessageStorage(IOfflineMessageStorage messageStorage)
    {
        if (messageStorage == null) return;

        _messageStorage = messageStorage;
    }

    public void setOnlineSessionStorage(IOnlineSessionStorage sessionStorage)
    {
        if (sessionStorage == null) return;

        _sessionStorage = sessionStorage;
    }

    public void setRequestValidator(IRequestValidator requestValidator)
    {
        if (requestValidator == null) return;

        _requestValidator = requestValidator;
    }

    public void registerReceiveExecutor(ImMessageType messageType, Func<ImMessageRequest, ServerMessageReceiveExecutor> executorFunction)
    {
        if (executorFunction == null)
            throw new NullPointException("The param of ServerMessageReceiveExecutor is empty.");

        //if (MessageExecuteFactory.isExists(messageType))
        //    throw new Exception("Duplicate message types");

        ServerMessageExecuteFactory.registerReceiveExecutor(messageType, executorFunction);
    }

    public void start()
    {
        serverEngine = new IMServerEngine(this.Host, this.Port);
        serverEngine.setRequestValidator(this._requestValidator);

        OnlineChannelManager.setSessionStorage(this._sessionStorage);
        ServerMessageOperator.setOfflineMessageStorage(this._messageStorage);

        this.initDefaultReceiveExecutor();

        serverEngine.bind();
    }

    private void initDefaultReceiveExecutor()
    {
        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.UserValidate,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {

                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new UserValidateReceiveExecutor(t.getUserValidateRequest());
                    }
                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.HeartBeat,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {

                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new HeartBeatMessageReceiveExecutor(t.getHeartBeatMessageRequest());
                    }
                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.ChatUser,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {

                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new ChatUserMessageReceiveExecutor(t.getChatUserMessageRequest());
                    }
                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.ChatGroup,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {

                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new ChatGroupMessageReceiveExecutor(t.getChatGroupMessageRequest());
                    }
                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.AddFriend,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {

                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new AddFriendMessageReceiveExecutor(t.getAddFriendMessageRequest());
                    }
                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.TestConnection,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {
                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new TestConnectionMessageReceiveExecutor(t.getTestConnectionMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.ClientAckMessage,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {
                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new AckMessageReceiveExecutor(t.getAckMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.ChatSetting,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {
                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new ChatSettingMessageReceiveExecutor(t.getChatSettingMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.GroupOperate,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {
                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new GroupOperateMessageReceiveExecutor(t.getGroupOperateMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.NearCircleOperate,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {
                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new NearCircleOperateMessageReceiveExecutor(t.getNearCircleOperateMessageRequest());
                    }

                });

        ServerMessageExecuteFactory.registerReceiveExecutor(ImMessageType.CircleOperate,
                new Function<ImMessageRequest, MessageReceiveExecutor>() {
                    @Override
                    public MessageReceiveExecutor apply(ImMessageRequest t) {
                        return new CircleOperateMessageReceiveExecutor(t.getCircleOperateMessageRequest());
                    }

                });
    }
}