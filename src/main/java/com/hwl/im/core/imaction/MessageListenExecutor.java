package com.hwl.im.core.imaction;

import com.hwl.im.improto.ImMessageContext;

public interface MessageListenExecutor {
    void execute(ImMessageContext messageContext);

    boolean executedAndClose();

    // ImMessageType getMessageType();
}