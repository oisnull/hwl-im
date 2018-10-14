package com.hwl.im.client.listen;

import com.hwl.im.core.imaction.AbstractMessageListenExecutor;
import com.hwl.imcore.improto.ImAddFriendMessageResponse;
import com.hwl.imcore.improto.ImMessageResponse;
import com.hwl.imcore.improto.ImMessageType;

public class AddFriendMessageListen extends AbstractMessageListenExecutor<ImAddFriendMessageResponse> {

    @Override
    public void executeCore(ImMessageType messageType, ImAddFriendMessageResponse
            imAddFriendMessageResponse) {


    }

    @Override
    public ImAddFriendMessageResponse getResponse(ImMessageResponse response) {
        return response.getAddFriendMessageResponse();
    }
}
