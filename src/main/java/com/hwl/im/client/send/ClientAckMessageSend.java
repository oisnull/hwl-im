package com.hwl.im.client.send;

import com.hwl.im.core.imaction.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImAckMessageRequest;
import com.hwl.imcore.improto.ImMessageRequest;
import com.hwl.imcore.improto.ImMessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public class ClientAckMessageSend extends AbstractMessageSendExecutor {

    static Logger log = LogManager.getLogger(ClientAckMessageSend.class.getName());
    long fromUserId = 0;
    String messageid = "";

    public ClientAckMessageSend(long fromUserId, String messageid) {
        this.fromUserId = fromUserId;
        this.messageid = messageid;
    }

    @Override
    public Consumer<Boolean> sendStatusCallback() {
        return null;
    }

    @Override
    public void setRequestBody(ImMessageRequest.Builder request) {
        request.setAckMessageRequest(ImAckMessageRequest.newBuilder().setFromUserId(fromUserId).setMessageid(messageid).build());
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.ClientAckMessage;
    }
}