package com.hwl.im.client.send;

import com.hwl.im.client.core.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImMessageRequest.Builder;
import com.hwl.imcore.improto.ImMessageType;
import com.hwl.imcore.improto.ImUserValidateRequest;

public class UserValidateSend extends AbstractMessageSendExecutor {

    long userId = 0L;
    String token = "";

    public UserValidateSend(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.UserValidate;
    }

    @Override
    public void setRequestBody(Builder request) {
        ImUserValidateRequest userValidateRequest = ImUserValidateRequest.newBuilder().setUserId(userId).setToken(token)
                .build();
        request.setUserValidateRequest(userValidateRequest);
    }
}