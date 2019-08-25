package com.hwl.im.client.core;

import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageResponse;
import com.hwl.imcore.improto.ImMessageResponseCode;
import com.hwl.imcore.improto.ImMessageResponseHead;
import com.hwl.imcore.improto.ImMessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractMessageListenExecutor<TResponse> implements IClientMessageListenExecutor {
    static Logger log = LogManager.getLogger(AbstractMessageListenExecutor.class.getName());

    private ImMessageResponseHead responseHead = null;
    private TResponse responseBody = null;

    @Override
    public final void execute(ImMessageContext messageContext) {
        responseHead = messageContext.getResponse().getResponseHead();
        responseBody = this.getResponse(messageContext.getResponse());

        if (this.checkResponse()) {
            this.executeCore(messageContext.getType(), responseBody);
        }
    }

    private boolean checkResponse() {
        boolean flag = false;
        if (responseHead == null || responseBody == null) {
            failure(0, "The response head or body in ImMessageContext is empty.");
        } else {
            switch (responseHead.getCode()) {
                case ImMessageResponseCode.Success_VALUE:
                    success(responseBody);
                    flag = true;
                    break;
                case ImMessageResponseCode.SessionidInvalid_VALUE:
                    sessionidInvalid();
                    break;
                case ImMessageResponseCode.Failed_VALUE:
                default:
                    failure(responseHead.getCode(), responseHead.getMessage());
                    break;
            }
        }
        return flag;
    }

    public final ImMessageResponseHead getResponseHead() {
        return responseHead;
    }

    @Override
    public boolean executedAndClose() {
        return false;
    }

    public abstract TResponse getResponse(ImMessageResponse response);

    public abstract void executeCore(ImMessageType messageType, TResponse response);

    public void sessionidInvalid() {
        log.debug("Client message listen : session id invalid.");
    }

    public void success(TResponse response) {
    }

    public void failure(int code, String message) {
        log.debug("Client message listen : {}:{}.", code, message);
    }
}