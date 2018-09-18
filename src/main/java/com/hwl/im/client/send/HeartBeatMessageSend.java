package com.hwl.im.client.send;

import java.util.function.Consumer;

import com.hwl.im.core.imaction.AbstractMessageSendExecutor;
import com.hwl.im.core.proto.ImHeartBeatMessageRequest;
import com.hwl.im.core.proto.ImMessageRequest.Builder;
import com.hwl.im.core.proto.ImMessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeartBeatMessageSend extends AbstractMessageSendExecutor {

    static Logger log = LogManager.getLogger(HeartBeatMessageSend.class.getName());

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.HeartBeat;
    }

    @Override
    public void setRequestBody(Builder request) {
        request.setHeartBeatMessageRequest(
                ImHeartBeatMessageRequest.newBuilder().setCurrentTime(System.currentTimeMillis()).build());
    }

	@Override
	public Consumer<Boolean> sendStatusCallback() {
		return null;
	}
}