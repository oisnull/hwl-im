package com.hwl.im.core.imaction;

import com.hwl.im.core.immode.MessageRequestHeadOperate;
import com.hwl.im.core.proto.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractMessageSendExecutor implements MessageSendExecutor {

    static Logger log = LogManager.getLogger(AbstractMessageSendExecutor.class.getName());

    @Override
    public final ImMessageContext getMessageContext() {
        // if (!checkBody())
        // return null;
        ImMessageRequest.Builder request = ImMessageRequest.newBuilder();
        request.setRequestHead(getMessageHead());

        try {
            this.setRequestBody(request);
        } catch (Exception e) {
            log.debug("Client build message content error, info : {}", e.getMessage());
            return null;
        }

        ImMessageContext.Builder context = ImMessageContext.newBuilder();
        context.setRequest(request);
        context.setType(getMessageType());

        return context.build();
    }

    @Override
    public boolean isSendFailedAndClose() {
        return false;
    }

    public ImMessageRequestHead getMessageHead() {
        return MessageRequestHeadOperate.buildRequestHead();
    }

    // public abstract boolean checkBody();

    public abstract void setRequestBody(final ImMessageRequest.Builder request);

}