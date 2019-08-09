package com.hwl.im.client.core;

import java.util.function.Consumer;

import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageRequest;
import com.hwl.imcore.improto.ImMessageRequestHead;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractMessageSendExecutor {

    static Logger log = LogManager.getLogger(AbstractMessageSendExecutor.class.getName());

    @Override
    public final ImMessageContext getMessageContext() {
        ImMessageRequest.Builder request = ImMessageRequest.newBuilder();
        request.setRequestHead(getMessageHead());

        this.setRequestBody(request);

        ImMessageContext.Builder context = ImMessageContext.newBuilder();
        context.setRequest(request);
        context.setType(getMessageType());

        return context.build();
    }

    public ImMessageRequestHead getMessageHead() {
        return MessageRequestHeadManager.getRequestHead();
    }

    public abstract void setRequestBody(final ImMessageRequest.Builder request);

    public abstract ImMessageType getMessageType();
	
    public void success()
    {
        log.debug("Client send "+getMessageType().toString()+" message to server success");
    }

    public void failure(string message)
    {
        log.debug("Client send "+getMessageType().toString()+" message to server failure. "+message);
    }
}