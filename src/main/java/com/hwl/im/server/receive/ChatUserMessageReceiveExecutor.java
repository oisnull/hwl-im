package com.hwl.im.server.receive;

import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.imcore.improto.ImChatUserMessageRequest;
import com.hwl.imcore.improto.ImChatUserMessageResponse;
import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageResponse.Builder;

public class ChatUserMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImChatUserMessageRequest> {

    public ChatUserMessageReceiveExecutor(ImChatUserMessageRequest imChatUserMessageRequest) {
        super(imChatUserMessageRequest);
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getChatUserMessageContent() == null)
            throw new NullPointerException("ChatUserMessageContent");
        if (request.getChatUserMessageContent().getToUserId() <= 0)
            throw new NullPointerException("toUserId");
    }

    @Override
    public boolean isAck() {
        return true;
    }

    @Override
    public void executeCore(Builder response) {
        response.setChatUserMessageResponse(
                ImChatUserMessageResponse.newBuilder().setChatUserMessageContent(request.getChatUserMessageContent())
                        .setBuildTime(System.currentTimeMillis()).build());
        ImMessageContext messageContext = super.getMessageContext(response);

        // fetch to user's sessionid
        // check user is online or not
        // if online and sent info
        // else store the message into memory
        long userid = request.getChatUserMessageContent().getToUserId();
        ServerMessageOperator.getInstance().push(userid, messageContext, false);
    }
}
