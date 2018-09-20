package com.hwl.im.client.listen;

import com.hwl.im.core.imaction.AbstractMessageListenExecutor;
import com.hwl.im.improto.ImChatUserMessageResponse;
import com.hwl.im.improto.ImMessageResponse;
import com.hwl.im.improto.ImMessageType;

public class ChatUserMessageListen extends AbstractMessageListenExecutor<ImChatUserMessageResponse> {

    @Override
    public void executeCore(ImMessageType messageType, ImChatUserMessageResponse response) {
        super.executeCore(messageType, response);
        System.out.println("ChatUserMessageReceive success : " + response.getChatUserMessageContent().toString());
    }

    @Override
    public ImChatUserMessageResponse getResponse(ImMessageResponse response) {
        return response.getChatUserMessageResponse();
    }

}