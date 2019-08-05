package com.hwl.im.server.receive;

import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.imcore.improto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CircleOperateMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImCircleOperateMessageRequest> {

    static Logger log = LogManager.getLogger(CircleOperateMessageReceiveExecutor.class.getName());
    ImCircleOperateMessageContent operateMessageContent = null;

    public CircleOperateMessageReceiveExecutor(ImCircleOperateMessageRequest operateMessageContent) {
        super(operateMessageContent);
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getCircleOperateMessageContent() == null) {
            throw new NullPointerException("CircleOperateMessageContent");
        } else {
            operateMessageContent = request.getCircleOperateMessageContent();
        }
    }

    @Override
    public void executeCore(ImMessageResponse.Builder response) {
        response.setCircleOperateMessageResponse(
                ImCircleOperateMessageResponse.newBuilder().setCircleOperateMessageContent(operateMessageContent)
                        .setBuildTime(System.currentTimeMillis()).build());
        ImMessageContext messageContext = super.getMessageContext(response);

        List<Long> userIds = new ArrayList<>();
        if (request.getCircleOperateMessageContent().getOriginUser().getUserId() != request.getCircleOperateMessageContent().getPostUser().getUserId())
            userIds.add(request.getCircleOperateMessageContent().getOriginUser().getUserId());
//        userIds.add(request.getCircleOperateMessageContent().getPostUser().getUserId());
        if (request.getCircleOperateMessageContent().hasReplyUser() &&
                request.getCircleOperateMessageContent().getReplyUser().getUserId() != request.getCircleOperateMessageContent().getPostUser().getUserId()) {
            userIds.add(request.getCircleOperateMessageContent().getReplyUser().getUserId());
        }

        if (userIds.size() > 0)
            for (Long userId : userIds) {
                ServerMessageOperator.getInstance().push(userId, messageContext, false);
            }
    }

    @Override
    protected boolean isAck() {
        return true;
    }
}
