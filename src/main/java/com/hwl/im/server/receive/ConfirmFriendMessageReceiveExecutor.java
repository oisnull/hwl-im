package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceivExecutor;
import com.hwl.imcore.improto.ImConfirmFriendMessageRequest;
import com.hwl.imcore.improto.ImMessageResponse;
import com.hwl.imcore.improto.ImMessageType;

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
