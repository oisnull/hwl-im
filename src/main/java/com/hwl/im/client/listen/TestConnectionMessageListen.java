package com.hwl.im.client.listen;

import com.hwl.im.client.core.AbstractMessageListenExecutor;
import com.hwl.imcore.improto.ImMessageResponse;
import com.hwl.imcore.improto.ImMessageType;
import com.hwl.imcore.improto.ImTestConnectionMessageResponse;

public class TestConnectionMessageListen extends AbstractMessageListenExecutor<ImTestConnectionMessageResponse> {
    @Override
    public ImTestConnectionMessageResponse getResponse(ImMessageResponse response) {
        return response.getTestConnectionMessageResponse();
    }

    @Override
    public void executeCore(ImMessageType messageType, ImTestConnectionMessageResponse imTestConnectionMessageResponse) {

    }

    @Override
    public void success(ImTestConnectionMessageResponse imTestConnectionMessageResponse) {
        super.success(imTestConnectionMessageResponse);
    }
}
