package com.hwl.im.client.send;

import com.hwl.im.client.core.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImAddFriendMessageContent;
import com.hwl.imcore.improto.ImAddFriendMessageRequest;
import com.hwl.imcore.improto.ImMessageRequest;
import com.hwl.imcore.improto.ImMessageType;

import java.util.function.Consumer;

public class AddFriendMessageSend extends AbstractMessageSendExecutor {

    ImAddFriendMessageContent messageContent;

    public AddFriendMessageSend(Long fromUserId, String fromUserName, String fromUserImage, Long toUserId, String content) {
        messageContent = ImAddFriendMessageContent.newBuilder()
                .setFromUserId(fromUserId)
                .setFromUserName(fromUserName)
                .setFromUserHeadImage(fromUserImage)
                .setToUserId(toUserId)
                .setContent(content)
                .build();
    }

    @Override
    public void setRequestBody(ImMessageRequest.Builder request) {
        request.setAddFriendMessageRequest(
                ImAddFriendMessageRequest.newBuilder().setAddFriendMessageContent(messageContent)
                        .build());
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.AddFriend;
    }
}
