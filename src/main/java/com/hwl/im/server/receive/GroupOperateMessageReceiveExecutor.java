package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceiveExecutor;
import com.hwl.im.core.immode.MessageOperate;
import com.hwl.im.server.redis.GroupStorage;
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
    public ImMessageType getMessageType() {
        return ImMessageType.GroupOperate;
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

        List<Long> userIds = GroupStorage.getGroupUsers(groupOperateMessageContent.getGroupGuid());
        //remove current userid
        userIds.remove(groupOperateMessageContent.getOperateUser().getUserId());
        if (userIds == null || userIds.size() <= 0) return;

        for (Long userid : userIds) {
             MessageOperate.serverPushOnline(userid, messageContext, (succ) -> {
                if (succ) {
                    log.debug("Server push group operate message success : {}", messageContext.toString());
                } else {
                    log.error("Server push group operate message failed : {}", messageContext.toString());
                }
            });
        }
    }

    @Override
    protected boolean isAck() {
        return true;
    }
}
