package com.hwl.im.client.listen;

import com.hwl.im.core.imaction.AbstractMessageListenExecutor;
import com.hwl.imcore.improto.ImMessageResponse;
import com.hwl.imcore.improto.ImTestConnectionMessageResponse;

public class TestConnectionMessageListen extends AbstractMessageListenExecutor<ImTestConnectionMessageResponse> {
    @Override
    public ImTestConnectionMessageResponse getResponse(ImMessageResponse response) {
        return response.getTestConnectionMessageResponse();
    }

    @Override
    public void success(ImTestConnectionMessageResponse imTestConnectionMessageResponse) {
        super.success(imTestConnectionMessageResponse);
    }

    @Override
    public void failed(int responseCode, String message) {
        super.failed(responseCode, message);
    }
}
