package com.hwl.im.server.receive;

import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.im.server.redis.store.GroupStore;
import com.hwl.imcore.improto.*;
import com.hwl.imcore.improto.ImMessageResponse.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GroupOperateMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImGroupOperateMessageRequest> {

    static Logger log = LogManager.getLogger(GroupOperateMessageReceiveExecutor.class.getName());
    ImGroupOperateMessageContent groupOperateMessageContent = null;

    public GroupOperateMessageReceiveExecutor(ImGroupOperateMessageRequest groupOperateMessageRequest) {
        super(groupOperateMessageRequest);
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getGroupOperateMessageContent() == null) {
            throw new NullPointerException("GroupOperateMessageContent");
        } else {
            groupOperateMessageContent = request.getGroupOperateMessageContent();
        }
    }

    @Override
    public void executeCore(Builder response) {
        response.setGroupOperateMessageResponse(
                ImGroupOperateMessageResponse.newBuilder().setGroupOperateMessageContent(groupOperateMessageContent)
                        .setBuildTime(System.currentTimeMillis()).build());
        ImMessageContext messageContext = super.getMessageContext(response);

        List<Long> userIds = GroupStore.getGroupUsers(groupOperateMessageContent.getGroupGuid());
        //remove current userid
        userIds.remove(groupOperateMessageContent.getOperateUser().getUserId());
        if (userIds == null || userIds.size() <= 0) return;

        for (Long userid : userIds) {
            ServerMessageOperator.getInstance().push(userid, messageContext, false);
        }
    }

    @Override
    protected boolean isAck() {
        return true;
    }
}
