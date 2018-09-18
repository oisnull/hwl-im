package com.hwl.im.core.imaction;

import com.hwl.im.core.proto.ImMessageContext;
import com.hwl.im.core.proto.ImMessageType;

public interface MessageSendExecutor {

    ImMessageType getMessageType();

    ImMessageContext getMessageContext();

    void sendResultCallback(boolean isSuccess);

    boolean isSendFailedAndClose();
}