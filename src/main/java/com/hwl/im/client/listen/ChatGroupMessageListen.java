package com.hwl.im.client.listen;

import com.hwl.im.client.core.AbstractMessageListenExecutor;
import com.hwl.imcore.improto.ImChatGroupMessageResponse;
import com.hwl.imcore.improto.ImMessageResponse;
import com.hwl.imcore.improto.ImMessageType;

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