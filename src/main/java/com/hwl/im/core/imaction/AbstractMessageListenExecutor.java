
package com.hwl.im.core.imaction;

import com.hwl.im.improto.ImMessageContext;
import com.hwl.im.improto.ImMessageResponse;
import com.hwl.im.improto.ImMessageResponseCode;
import com.hwl.im.improto.ImMessageResponseHead;
import com.hwl.im.improto.ImMessageType;

public abstract class AbstractMessageListenExecutor<TResponse> implements MessageListenExecutor {

    @Override
    public final void execute(ImMessageContext messageContext) {
        ImMessageType messageType = messageContext.getType();
        ImMessageResponse response = messageContext.getResponse();

        if (response != null) {
            TResponse messageResponse = this.getResponse(response);
            this.executeCore(messageType, messageResponse);

            ImMessageResponseHead responseHead = response.getResponseHead();
            if (responseHead.getCode() == ImMessageResponseCode.Success_VALUE) {
                success(messageResponse);
            } else if (responseHead.getCode() == ImMessageResponseCode.SessionidInvalid_VALUE) {
                sessionidInvalid();
            } else {
                failed(responseHead.getCode(), responseHead.getMessage());
            }
        }
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