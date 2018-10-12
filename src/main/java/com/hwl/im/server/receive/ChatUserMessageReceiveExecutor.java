package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceivExecutor;
import com.hwl.im.core.immode.MessageOperate;
import com.hwl.im.improto.ImChatUserMessageRequest;
import com.hwl.im.improto.ImChatUserMessageResponse;
import com.hwl.im.improto.ImMessageContext;
import com.hwl.im.improto.ImMessageType;
import com.hwl.im.improto.ImMessageResponse.Builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatUserMessageReceiveExecutor extends AbstractMessageReceivExecutor<ImChatUserMessageRequest> {

    static Logger log = LogManager.getLogger(ChatUserMessageReceiveExecutor.class.getName());

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
    public void executeCore(Builder response) {
        response.setChatUserMessageResponse(
                ImChatUserMessageResponse.newBuilder().setChatUserMessageContent(request.getChatUserMessageContent())
                        .setBuildTime(System.currentTimeMillis()).build());
        ImMessageContext messageContext = super.getMessageContext(response);

        // fetch to user's sessionid
        // check user is online or not
        // if online and sent info
        // else store the message into memory
        Long userid = request.getChatUserMessageContent().getToUserId();
        MessageOperate.serverSendAndRetry(userid, messageContext, (succ) -> {
            if (succ) {
                log.debug("Server push chat user message success : {}", messageContext.toString());
            } else {
                log.error("Server push chat user message failed : {}", messageContext.toString());
            }
        });
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.ChatUser;
    }
}
