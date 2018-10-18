package com.hwl.im.client.listen;

import com.hwl.im.core.imaction.AbstractMessageListenExecutor;
import com.hwl.imcore.improto.ImChatUserMessageResponse;
import com.hwl.imcore.improto.ImMessageResponse;
import com.hwl.imcore.improto.ImMessageType;

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