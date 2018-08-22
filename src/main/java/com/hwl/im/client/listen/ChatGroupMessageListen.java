package com.hwl.im.client.listen;

import com.hwl.im.core.imaction.AbstractMessageListenExecutor;
import com.hwl.im.core.proto.ImChatGroupMessageResponse;
import com.hwl.im.core.proto.ImMessageResponse;
import com.hwl.im.core.proto.ImMessageType;

public class ChatGroupMessageListen extends AbstractMessageListenExecutor<ImChatGroupMessageResponse> {

    @Override
    public void executeCore(ImMessageType messageType, ImChatGroupMessageResponse response) {
        System.out.println("ChatGroupMessageReceive success : " + response.getChatGroupMessageContent().toString());
    }

    @Override
    public ImChatGroupMessageResponse getResponse(ImMessageResponse response) {
        return response.getChatGroupMessageResponse();
    }

}