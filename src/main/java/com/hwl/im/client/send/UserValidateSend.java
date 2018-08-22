package com.hwl.im.client.send;

import com.hwl.im.core.proto.ImMessageRequest.Builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hwl.im.core.imaction.AbstractMessageSendExecutor;
import com.hwl.im.core.proto.ImMessageType;
import com.hwl.im.core.proto.ImUserValidateRequest;

public class UserValidateSend extends AbstractMessageSendExecutor {

    static Logger log = LogManager.getLogger(UserValidateSend.class.getName());

    Long userId = 0L;
    String token = "";

    public UserValidateSend(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public void getSendResult(boolean isSuccess) {
        log.debug("User validate send {} , userId:{} , token:{}", isSuccess ? "success" : "failed", userId, token);
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