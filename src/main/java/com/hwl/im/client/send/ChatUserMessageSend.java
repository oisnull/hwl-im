package com.hwl.im.client.send;

import java.security.InvalidParameterException;
import java.util.function.Consumer;

import com.hwl.im.core.imaction.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImChatUserMessageContent;
import com.hwl.imcore.improto.ImChatUserMessageRequest;
import com.hwl.imcore.improto.ImMessageRequest.Builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hwl.imcore.improto.ImMessageType;

public class ChatUserMessageSend extends AbstractMessageSendExecutor {

    static Logger log = LogManager.getLogger(ChatUserMessageSend.class.getName());

    Long fromUserId, toUserId = 0L;
    String content = "";

    public ChatUserMessageSend(Long fromUserId, Long toUserId, String content) {
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

        this.checkParams();

        ImChatUserMessageContent messageContent = ImChatUserMessageContent.newBuilder().setFromUserId(fromUserId)
                .setToUserId(toUserId).setContent(content).build();
        request.setChatUserMessageRequest(
                ImChatUserMessageRequest.newBuilder().setChatUserMessageContent(messageContent).build());
    }

    private void checkParams() {
        if (fromUserId <= 0 || toUserId <= 0 || fromUserId == toUserId) {
            throw new InvalidParameterException("FromUserId cannot be the same as toUserId or is zero");
        }
        if (content == null || content.isEmpty()) {
            throw new NullPointerException("Send chat user message content cannot be null or empty");
        }
    }

	@Override
	public Consumer<Boolean> sendStatusCallback() {
		return null;
	}

}