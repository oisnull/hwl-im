package com.hwl.im.client.send;

import java.util.function.Consumer;

import com.hwl.im.core.imaction.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImAckMessageRequest;
import com.hwl.imcore.improto.ImHeartBeatMessageRequest;
import com.hwl.imcore.improto.ImMessageRequest.Builder;
import com.hwl.imcore.improto.ImMessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class ClientAckMessageSend extends AbstractMessageSendExecutor {

    static Logger log = LogManager.getLogger(ClientAckMessageSend.class.getName());
	
    long userId = 0L;
	String messageid;
	
	public ClientAckMessageSend(long userId,String messageid) {
        this.userId = userId;
		this.messageid = messageid;
    }
	
    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.ClientAckMessage;
    }

    @Override
    public void setRequestBody(Builder request) {
        request.setAckMessageRequest(
                ImAckMessageRequest.newBuilder()
				.setFromUserId(userId)
				.setMessageId(messageid)
				.build());
    }

	@Override
	public Consumer<Boolean> sendStatusCallback() {
		return null;
	}
}
