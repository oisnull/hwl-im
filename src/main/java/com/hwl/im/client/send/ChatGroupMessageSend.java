package com.hwl.im.client.send;

import java.util.function.Consumer;

import com.hwl.im.core.imaction.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImChatGroupMessageContent;
import com.hwl.imcore.improto.ImChatGroupMessageRequest;
import com.hwl.imcore.improto.ImMessageRequest.Builder;
import com.hwl.imcore.improto.ImMessageType;
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

    // @Override
    // public void sendResultCallback(boolean isSuccess) {
    //     log.debug("Client send chat user message to im server {}", isSuccess ? "success" : "failed");
    // }

    @Override
    public void setRequestBody(Builder request) {
        ImChatGroupMessageContent messageContent = ImChatGroupMessageContent.newBuilder().setFromUserId(fromUserId)
                .setToGroupGuid(groupGuid).setContent(content).build();
        request.setChatGroupMessageRequest(
                ImChatGroupMessageRequest.newBuilder().setChatGroupMessageContent(messageContent).build());
    }

	@Override
	public Consumer<Boolean> sendStatusCallback() {
		return null;
	}
}
