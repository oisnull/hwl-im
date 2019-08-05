package com.hwl.im.server.receive;

import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.im.server.redis.store.GroupStore;
import com.hwl.imcore.improto.*;
import com.hwl.imcore.improto.ImMessageResponse.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ChatSettingMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImChatSettingMessageRequest> {

    static Logger log = LogManager.getLogger(ChatSettingMessageReceiveExecutor.class.getName());
    ImChatSettingMessageContent settingMessageContent = null;

    public ChatSettingMessageReceiveExecutor(ImChatSettingMessageRequest chatSettingMessageRequest) {
        super(chatSettingMessageRequest);
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getChatSettingMessageContent() == null) {
            throw new NullPointerException("ChatSettingMessageContent");
        } else {
            settingMessageContent = request.getChatSettingMessageContent();
        }
    }

    @Override
    public void executeCore(Builder response) {
        response.setChatSettingMessageResponse(
                ImChatSettingMessageResponse.newBuilder().setChatSettingMessageContent(settingMessageContent)
                        .setBuildTime(System.currentTimeMillis()).build());
        ImMessageContext messageContext = super.getMessageContext(response);

        List<Long> userIds = GroupStore.getGroupUsers(settingMessageContent.getGroupGuid());
        if (userIds == null || userIds.size() <= 0) return;

        //remove current userid
        userIds.remove(settingMessageContent.getSettingUser().getUserId());

        for (Long userid : userIds) {
            ServerMessageOperator.getInstance().push(userid, messageContext, false);
        }
    }

    @Override
    protected boolean isAck() {
        return true;
    }
}
