package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceiveExecutor;
import com.hwl.im.core.immode.MessageOperate;
import com.hwl.imcore.improto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class NearCircleOperateMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImNearCircleOperateMessageRequest> {

    static Logger log = LogManager.getLogger(NearCircleOperateMessageReceiveExecutor.class.getName());
    ImNearCircleOperateMessageContent operateMessageContent = null;

    public NearCircleOperateMessageReceiveExecutor(ImNearCircleOperateMessageRequest operateMessageContent) {
        super(operateMessageContent);
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.NearCircleOperate;
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getNearCircleOperateMessageContent() == null) {
            throw new NullPointerException("NearCircleOperateMessageContent");
        } else {
            operateMessageContent = request.getNearCircleOperateMessageContent();
        }
    }

    @Override
    public void executeCore(ImMessageResponse.Builder response) {
        response.setNearCircleOperateMessageResponse(
                ImNearCircleOperateMessageResponse.newBuilder().setNearCircleOperateMessageContent(operateMessageContent)
                        .setBuildTime(System.currentTimeMillis()).build());
        ImMessageContext messageContext = super.getMessageContext(response);

        List<Long> userIds = new ArrayList<>();
        if (request.getNearCircleOperateMessageContent().getOriginUser().getUserId() != request.getNearCircleOperateMessageContent().getPostUser().getUserId())
            userIds.add(request.getNearCircleOperateMessageContent().getOriginUser().getUserId());
//        userIds.add(request.getNearCircleOperateMessageContent().getPostUser().getUserId());
        if (request.getNearCircleOperateMessageContent().hasReplyUser() &&
                request.getNearCircleOperateMessageContent().getReplyUser().getUserId() != request.getNearCircleOperateMessageContent().getPostUser().getUserId()) {
            userIds.add(request.getNearCircleOperateMessageContent().getReplyUser().getUserId());
        }

        if (userIds.size() > 0)
            for (Long userId : userIds) {
                MessageOperate.serverPushOnline(userId, messageContext, null);
            }
    }

    @Override
    protected boolean isAck() {
        return true;
    }
}
