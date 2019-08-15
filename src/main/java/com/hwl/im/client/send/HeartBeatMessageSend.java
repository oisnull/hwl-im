package com.hwl.im.client.send;

import com.hwl.im.client.core.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImHeartBeatMessageRequest;
import com.hwl.imcore.improto.ImMessageRequest.Builder;
import com.hwl.imcore.improto.ImMessageType;

public class HeartBeatMessageSend extends AbstractMessageSendExecutor {

    private long userId;

    public HeartBeatMessageSend(long userId) {
        this.userId = userId;
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.HeartBeat;
    }

    @Override
    public void setRequestBody(Builder request) {
        request.setHeartBeatMessageRequest(ImHeartBeatMessageRequest.newBuilder().setFromUserId(userId)
                .setCurrentTime(System.currentTimeMillis()).build());
    }
}
