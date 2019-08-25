package com.hwl.im.server.receive;

import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.im.server.redis.store.GroupStore;
import com.hwl.imcore.improto.ImChatGroupMessageContent;
import com.hwl.imcore.improto.ImChatGroupMessageRequest;
import com.hwl.imcore.improto.ImChatGroupMessageResponse;
import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageResponse.Builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ChatGroupMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImChatGroupMessageRequest> {

    static Logger log = LogManager.getLogger(ChatGroupMessageReceiveExecutor.class.getName());
    ImChatGroupMessageContent groupMessageContent = null;

    public ChatGroupMessageReceiveExecutor(ImChatGroupMessageRequest imChatGroupMessageRequest) {
        super(imChatGroupMessageRequest);
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getChatGroupMessageContent() == null) {
            throw new NullPointerException("ChatGroupMessageContent");
        } else {
            groupMessageContent = request.getChatGroupMessageContent();
        }
        if (groupMessageContent.getToGroupGuid() == null || groupMessageContent.getToGroupGuid().isEmpty()) {
            throw new NullPointerException("GroupGuid");
        }
    }

    @Override
    public void executeCore(Builder response) {
        response.setChatGroupMessageResponse(
                ImChatGroupMessageResponse.newBuilder().setChatGroupMessageContent(groupMessageContent)
                        .setBuildTime(System.currentTimeMillis()).build());
        ImMessageContext messageContext = super.getMessageContext(response);

        // get group user list by guid
        // check user list is online
        // online user then send msg
        // offline user then set msg
        List<Long> userIds = GroupStore.getGroupUsers(groupMessageContent.getToGroupGuid());
        if (userIds == null || userIds.size() <= 0)
            return;

        //remove current userid
        userIds.remove(groupMessageContent.getFromUserId());

        for (Long userid : userIds) {
            ServerMessageOperator.getInstance().push(userid, messageContext, false);
        }
    }

    @Override
    public boolean isAck() {
        return true;
    }
}
