package com.hwl.im.core.imaction;

import com.hwl.im.improto.ImMessageContext;
import com.hwl.im.improto.ImMessageType;

public interface MessageSendExecutor {

    ImMessageType getMessageType();

    ImMessageContext getMessageContext();

    void sendResultCallback(boolean isSuccess);

    boolean isSendFailedAndClose();
}