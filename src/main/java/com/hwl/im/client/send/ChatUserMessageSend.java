package com.hwl.im.client.send;

import com.hwl.im.client.core.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImChatUserMessageContent;
import com.hwl.imcore.improto.ImChatUserMessageRequest;
import com.hwl.imcore.improto.ImMessageRequest.Builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hwl.imcore.improto.ImMessageType;

public class ChatUserMessageSend extends AbstractMessageSendExecutor {

    static Logger log = LogManager.getLogger(ChatUserMessageSend.class.getName());

    long fromUserId, toUserId = 0L;
    String content = "";

    public ChatUserMessageSend(long fromUserId, long toUserId, String content) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.content = content;
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.ChatUser;
    }

    @Override
    public void setRequestBody(Builder request) {
        ImChatUserMessageContent messageContent = ImChatUserMessageContent.newBuilder().setFromUserId(fromUserId)
                .setToUserId(toUserId).setContent(content).build();
        request.setChatUserMessageRequest(
                ImChatUserMessageRequest.newBuilder().setChatUserMessageContent(messageContent).build());
    }
}