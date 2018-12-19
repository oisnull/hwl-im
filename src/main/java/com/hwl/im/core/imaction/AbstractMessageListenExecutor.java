
package com.hwl.im.core.imaction;

import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageResponse;
import com.hwl.imcore.improto.ImMessageResponseCode;
import com.hwl.imcore.improto.ImMessageResponseHead;
import com.hwl.imcore.improto.ImMessageType;

public abstract class AbstractMessageListenExecutor<TResponse> implements MessageListenExecutor {

    private ImMessageResponseHead responseHead = null;

    @Override
    public final void execute(ImMessageContext messageContext) {
        ImMessageType messageType = messageContext.getType();
        ImMessageResponse response = messageContext.getResponse();

        if (response != null) {
            TResponse messageResponse = this.getResponse(response);
            responseHead = response.getResponseHead();

            this.executeCore(messageType, messageResponse);
            if (responseHead.getCode() == ImMessageResponseCode.Success_VALUE) {
                success(messageResponse);
            } else if (responseHead.getCode() == ImMessageResponseCode.SessionidInvalid_VALUE) {
                sessionidInvalid();
            } else {
                failed(responseHead.getCode(), responseHead.getMessage());
            }
        }
    }

    public final ImMessageResponseHead getResponseHead() {
        return responseHead;
    }

    @Override
    public boolean executedAndClose() {
        return false;
    }

    public abstract TResponse getResponse(ImMessageResponse response);

    public void executeCore(ImMessageType messageType, TResponse response) {
        if (response == null)
            return;
    }

    public void sessionidInvalid() {

    }

    public void success(TResponse response) {
    };

    public void failed(int responseCode, String message) {
    };
}