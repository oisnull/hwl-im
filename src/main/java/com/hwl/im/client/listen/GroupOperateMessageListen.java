package com.hwl.im.client.listen;

import com.hwl.im.core.imaction.AbstractMessageListenExecutor;
import com.hwl.imcore.improto.ImGroupOperateMessageContent;
import com.hwl.imcore.improto.ImGroupOperateMessageResponse;
import com.hwl.imcore.improto.ImMessageResponse;
import com.hwl.imcore.improto.ImMessageType;
import com.hwl.imcore.improto.ImUserContent;

public class GroupOperateMessageListen extends
        AbstractMessageListenExecutor<ImGroupOperateMessageResponse> {

    private ImGroupOperateMessageResponse response;
    private ImGroupOperateMessageContent messageContent;
    private ImUserContent operateUser;

    @Override
    public void executeCore(ImMessageType messageType, ImGroupOperateMessageResponse response) {
        super.executeCore(messageType, response);
        this.response = response;
        messageContent = response.getGroupOperateMessageContent();
        operateUser = messageContent.getOperateUser();
    }

    @Override
    public ImGroupOperateMessageResponse getResponse(ImMessageResponse response) {
        return response.getGroupOperateMessageResponse();
    }
}