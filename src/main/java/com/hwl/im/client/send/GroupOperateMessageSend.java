package com.hwl.im.client.send;

import com.hwl.im.core.imaction.AbstractMessageSendExecutor;
import com.hwl.imcore.improto.ImGroupOperateMessageContent;
import com.hwl.imcore.improto.ImGroupOperateMessageRequest;
import com.hwl.imcore.improto.ImGroupOperateType;
import com.hwl.imcore.improto.ImMessageRequest;
import com.hwl.imcore.improto.ImMessageType;
import com.hwl.imcore.improto.ImUserContent;

import java.util.List;
import java.util.function.Consumer;

public class GroupOperateMessageSend extends AbstractMessageSendExecutor {

    ImGroupOperateMessageContent messageContent;

    public GroupOperateMessageSend(ImGroupOperateType operateType,
                                   String groupGuid,
                                   String groupName) {
        this(operateType, groupGuid, groupName, null);
    }

    public GroupOperateMessageSend(ImGroupOperateType operateType,
                                   String groupGuid,
                                   String groupName,
                                   List<ImUserContent> groupUsers) {

//        ImUserContent operateUser = ImUserContent.newBuilder()
//                .setUserId(UserSP.getUserId())
//                .setUserName(UserSP.getUserShowName())
//                .setUserImage(UserSP.getUserHeadImage()).build();
//
//        ImGroupOperateMessageContent.Builder builder = ImGroupOperateMessageContent.newBuilder()
//                .setOperateType(operateType)
//                .setOperateUser(operateUser)
//                .setGroupGuid(StringUtils.nullStrToEmpty(groupGuid))
//                .setGroupName(StringUtils.nullStrToEmpty(groupName));

//        if (groupUsers != null && groupUsers.size() > 0) {
//            builder.addAllGroupUsers(groupUsers);
//        }
//
//        messageContent = builder.build();
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.GroupOperate;
    }

    @Override
    public Consumer<Boolean> sendStatusCallback() {
        return null;
    }

    @Override
    public void setRequestBody(ImMessageRequest.Builder request) {
        request.setGroupOperateMessageRequest(
                ImGroupOperateMessageRequest.newBuilder().setGroupOperateMessageContent
                        (messageContent)
                        .build());
    }
}
