package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceiveExecutor;
import com.hwl.im.core.immode.MessageOperate;
import com.hwl.imcore.improto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestConnectionMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImTestConnectionMessageRequest> {
    static Logger log = LogManager.getLogger(TestConnectionMessageReceiveExecutor.class.getName());
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

        MessageOperate.serverSendAndRetry(request.getFromUserId(), messageContext, (succ) -> {
            if (succ) {
                log.debug("Server push test connection message success : {}", messageContext.toString());
            } else {
                log.error("Server push test connection message failed : {}", messageContext.toString());
            }
        });
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.TestConnection;
    }
}
