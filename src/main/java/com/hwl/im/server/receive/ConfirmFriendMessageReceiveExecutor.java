package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceivExecutor;
import com.hwl.im.improto.ImConfirmFriendMessageRequest;
import com.hwl.im.improto.ImMessageResponse;
import com.hwl.im.improto.ImMessageType;

public class ConfirmFriendMessageReceiveExecutor extends AbstractMessageReceivExecutor<ImConfirmFriendMessageRequest> {
    public ConfirmFriendMessageReceiveExecutor(ImConfirmFriendMessageRequest imConfirmFriendMessageRequest) {
        super(imConfirmFriendMessageRequest);
    }

    @Override
    public void executeCore(ImMessageResponse.Builder response) {

    }

    @Override
    public ImMessageType getMessageType() {
        return null;
    }
}
