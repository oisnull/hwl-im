package com.hwl.im.client.send;

import com.hwl.im.client.core.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImChatGroupMessageContent;
import com.hwl.imcore.improto.ImChatGroupMessageRequest;
import com.hwl.imcore.improto.ImMessageRequest.Builder;
import com.hwl.imcore.improto.ImMessageType;

public class ChatGroupMessageSend extends AbstractMessageSendExecutor {

    long fromUserId;
    String groupGuid;
    String content;

    public ChatGroupMessageSend(long fromUserId, String groupGuid, String content) {
        this.fromUserId = fromUserId;
        this.groupGuid = groupGuid;
        this.content = content;
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.ChatGroup;
    }

    @Override
    public void setRequestBody(Builder request) {
        ImChatGroupMessageContent messageContent = ImChatGroupMessageContent.newBuilder().setFromUserId(fromUserId)
                .setToGroupGuid(groupGuid).setContent(content).build();
        request.setChatGroupMessageRequest(
                ImChatGroupMessageRequest.newBuilder().setChatGroupMessageContent(messageContent).build());
    }
}
