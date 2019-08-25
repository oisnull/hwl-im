package com.hwl.im.server.receive;

import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.imcore.improto.*;

public class TestConnectionMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImTestConnectionMessageRequest> {
    public TestConnectionMessageReceiveExecutor(ImTestConnectionMessageRequest imTestConnectionMessageRequest) {
        super(imTestConnectionMessageRequest);
    }

    @Override
    public void executeCore(ImMessageResponse.Builder response) {
        response.setTestConnectionMessageResponse(ImTestConnectionMessageResponse.newBuilder()
                .setContent("Hello client-" + request.getFromUserId())
                .setSendTime(System.currentTimeMillis())
                .build());
        ImMessageContext messageContext = super.getMessageContext(response);

        ServerMessageOperator.getInstance().push(request.getFromUserId(), messageContext, false);
    }
}
