package com.hwl.im.client.send;

import com.hwl.im.core.imaction.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImAckMessageRequest;

import java.util.function.Consumer;

import com.hwl.imcore.improto.ImMessageRequest.Builder;
import com.hwl.imcore.improto.ImMessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientAckMessageSend extends AbstractMessageSendExecutor {

    static Logger log = LogManager.getLogger(ClientAckMessageSend.class.getName());
    long fromUserId = 0;
    String messageid = "";

    public ClientAckMessageSend(long fromUserId, String messageid) {
        this.fromUserId = fromUserId;
        this.messageid = messageid;
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.ClientAckMessage;
    }

    @Override
    public void setRequestBody(Builder request) {
        request.setAckMessageRequest(ImAckMessageRequest.newBuilder()
                .setFromUserId(fromUserId)
                .setMessageid(messageid)
                .build());
        log.debug("Client send ack message ,userid({}) messageid({})", fromUserId, messageid);
    }
}
