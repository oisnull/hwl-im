package com.hwl.im.client.send;

import com.hwl.imcore.improto.ImMessageRequest.Builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

import com.hwl.im.core.imaction.AbstractMessageSendExecutor;
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