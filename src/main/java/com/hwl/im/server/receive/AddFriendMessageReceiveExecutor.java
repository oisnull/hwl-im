package com.hwl.im.server.receive;

import java.security.InvalidParameterException;

import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.imcore.improto.ImAddFriendMessageResponse;
import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageResponse;
import com.hwl.imcore.improto.ImAddFriendMessageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddFriendMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImAddFriendMessageRequest> {

    static Logger log = LogManager.getLogger(AddFriendMessageReceiveExecutor.class.getName());

    public AddFriendMessageReceiveExecutor(ImAddFriendMessageRequest imAddFriendMessageRequest) {
        super(imAddFriendMessageRequest);
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getAddFriendMessageContent() == null)
            throw new NullPointerException("AddFriendMessageContent");

        if (request.getAddFriendMessageContent().getFromUserId() <= 0) {
            throw new InvalidParameterException("fromUserId");
        }
        if (request.getAddFriendMessageContent().getToUserId() <= 0) {
            throw new InvalidParameterException("toUserId");
        }
    }

    @Override
    public void executeCore(ImMessageResponse.Builder response) {
        response.setAddFriendMessageResponse(
                ImAddFriendMessageResponse.newBuilder()
                        .setAddFriendMessageContent(request.getAddFriendMessageContent())
                        .setBuildTime(System.currentTimeMillis())
                        .build());
        ImMessageContext messageContext = super.getMessageContext(response);

        Long userid = request.getAddFriendMessageContent().getToUserId();
        ServerMessageOperator.getInstance().push(userid, messageContext, false);
    }

    @Override
    protected boolean isAck() {
        return true;
    }
}
