package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceivExecutor;
import com.hwl.im.core.immode.MessageOperate;
import com.hwl.im.core.imom.OnlineManage;
import com.hwl.im.core.imstore.OfflineMessageManage;
import com.hwl.im.improto.ImChatGroupMessageContent;
import com.hwl.im.improto.ImChatGroupMessageRequest;
import com.hwl.im.improto.ImChatGroupMessageResponse;
import com.hwl.im.improto.ImMessageContext;
import com.hwl.im.improto.ImMessageType;
import com.hwl.im.improto.ImMessageResponse.Builder;
import com.hwl.im.server.redis.GroupStorage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ChatGroupMessageReceiveExecutor extends AbstractMessageReceivExecutor<ImChatGroupMessageRequest> {

    static Logger log = LogManager.getLogger(ChatGroupMessageReceiveExecutor.class.getName());
    ImChatGroupMessageContent groupMessageContent = null;

    public ChatGroupMessageReceiveExecutor(ImChatGroupMessageRequest imChatGroupMessageRequest) {
        super(imChatGroupMessageRequest);
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.ChatGroup;
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getChatGroupMessageContent() == null) {
            throw new NullPointerException("ChatGroupMessageContent");
        } else {
            groupMessageContent = request.getChatGroupMessageContent();
        }
        if (groupMessageContent.getToGrouopGuid() == null || groupMessageContent.getToGrouopGuid().isEmpty()) {
            throw new NullPointerException("GroupGuid");
        }
    }

    @Override
    public void executeCore(Builder response) {
        response.setChatGroupMessageResponse(
                ImChatGroupMessageResponse.newBuilder().setChatGroupMessageContent(request.getChatGroupMessageContent())
                        .setBuildTime(System.currentTimeMillis()).build());
        ImMessageContext messageContext = super.getMessageContext(response);

        // get group user list by guid
        // check user list is online
        // online user then send msg
        // offline user then set msg
        List<Long> userIds = GroupStorage.getGroupUsers(groupMessageContent.getToGrouopGuid());
        if (userIds == null || userIds.size() <= 0)
            return;

        for (Long userid : userIds) {
            if (OnlineManage.getInstance().isOnline(userid)) {
                // online
                MessageOperate.serverSendAndRetry(userid, messageContext, (succ) -> {
                    if (succ) {
                        log.debug("Server push chat group message success : {}", messageContext.toString());
                    } else {
                        log.error("Server push chat group message failed : {}", messageContext.toString());
                    }
                });
            } else {
                // offline
                OfflineMessageManage.getInstance().addMessage(userid, messageContext);
            }
        }

    }
}
