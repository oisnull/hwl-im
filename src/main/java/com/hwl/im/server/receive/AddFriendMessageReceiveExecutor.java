package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceivExecutor;
import com.hwl.im.improto.ImAddFriendMessageRequest;
import com.hwl.im.improto.ImMessageResponse;
import com.hwl.im.improto.ImMessageType;

public class AddFriendMessageReceiveExecutor extends AbstractMessageReceivExecutor<ImAddFriendMessageRequest> {
    public AddFriendMessageReceiveExecutor(ImAddFriendMessageRequest imAddFriendMessageRequest) {
        super(imAddFriendMessageRequest);
    }

    @Override
    public void executeCore(ImMessageResponse.Builder response) {

    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.AddFriend;
    }
}
