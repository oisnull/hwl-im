package com.hwl.im.client.send;

import com.hwl.im.core.imaction.AbstractMessageSendExecutor;
import com.hwl.im.core.proto.ImChatGroupMessageContent;
import com.hwl.im.core.proto.ImChatGroupMessageRequest;
import com.hwl.im.core.proto.ImMessageRequest.Builder;
import com.hwl.im.core.proto.ImMessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatGroupMessageSend extends AbstractMessageSendExecutor {

    static Logger log = LogManager.getLogger(ChatUserMessageSend.class.getName());

    Long fromUserId;
    String groupGuid;
    String content;

    public ChatGroupMessageSend(Long fromUserId, String groupGuid, String content) {
        this.fromUserId = fromUserId;
        this.groupGuid = groupGuid;
        this.content = content;
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.ChatGroup;
    }

    @Override
    public void getSendResult(boolean isSuccess) {
        log.debug("Client send chat user message to im server {}", isSuccess ? "success" : "failed");
    }

    @Override
    public void setRequestBody(Builder request) {
        ImChatGroupMessageContent messageContent = ImChatGroupMessageContent.newBuilder().setFromUserId(fromUserId)
                .setToGrouopGuid(groupGuid).setContent(content).build();
        request.setChatGroupMessageRequest(
                ImChatGroupMessageRequest.newBuilder().setChatGroupMessageContent(messageContent).build());
    }
}
