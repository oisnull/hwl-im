package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceiveExecutor;
import com.hwl.imcore.improto.ImAckMessageRequest;
import com.hwl.imcore.improto.ImMessageResponse.Builder;
import com.hwl.imcore.improto.ImMessageType;

public class AckMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImAckMessageRequest> {

    public AckMessageReceiveExecutor(ImAckMessageRequest request) {
        super(request);
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.ClientAckMessage;
    }

    @Override
    public boolean isCheckSessionid() {
        return false;
    }

    @Override
    public void executeCore(Builder response) {

    }
}