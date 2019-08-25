package com.hwl.im.server.receive;

import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.imcore.improto.ImAckMessageRequest;
import com.hwl.imcore.improto.ImMessageResponse.Builder;

public class AckMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImAckMessageRequest> {

    public AckMessageReceiveExecutor(ImAckMessageRequest request) {
        super(request);
    }

    @Override
    public boolean isCheckSession() {
        return false;
    }

    @Override
    public void executeCore(Builder response) {
        ServerMessageOperator.getInstance().deleteSentMessage(request.getFromUserId(), request.getMessageid());
    }
}