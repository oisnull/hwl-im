package com.hwl.im.server.receive;

import com.hwl.im.server.action.OnlineChannelManager;
import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.im.server.redis.store.GroupStore;
import com.hwl.imcore.improto.*;

import io.netty.util.internal.StringUtil;

import java.util.List;

public class SystemMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImSystemMessageRequest> {

    ImSystemMessageContent systemMessageContent = null;

    public SystemMessageReceiveExecutor(ImSystemMessageRequest systemMessageRequest) {
        super(systemMessageRequest);
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getSystemMessageContent() == null) {
            throw new NullPointerException("ImSystemMessageContent");
        } else {
            systemMessageContent = request.getSystemMessageContent();
        }
    }

    @Override
    public void executeCore(ImMessageResponse.Builder response) {
        response.setSystemMessageResponse(ImSystemMessageResponse.newBuilder()
                .setSystemMessageContent(systemMessageContent).setBuildTime(System.currentTimeMillis()).build());
        ImMessageContext messageContext = super.getMessageContext(response);

        if (StringUtil.isNullOrEmpty(request.getToGroupGuid())) {
            ServerMessageOperator.getInstance().push(request.getToUserId(), messageContext, false);
        } else {
            List<Long> userIds = GroupStore.getGroupUsers(request.getToGroupGuid());
            if (userIds != null && userIds.size() > 0) {
                for (Long user : userIds) {
                    if (user.equals(request.getToUserId()) && !OnlineChannelManager.getInstance().isOnline(user)) {
                        continue;
                    }
                    ServerMessageOperator.getInstance().push(user, messageContext, false);
                }
            }
        }
    }
}
