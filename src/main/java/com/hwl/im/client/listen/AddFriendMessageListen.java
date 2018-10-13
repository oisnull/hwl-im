package com.hwl.im.client.listen;

import com.hwl.im.core.imaction.AbstractMessageListenExecutor;
import com.hwl.im.improto.ImAddFriendMessageResponse;
import com.hwl.im.improto.ImMessageResponse;
import com.hwl.im.improto.ImMessageType;

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
