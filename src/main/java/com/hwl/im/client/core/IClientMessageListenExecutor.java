package com.hwl.im.client.core;

import com.hwl.imcore.improto.ImMessageContext;

public interface IClientMessageListenExecutor {
    void execute(ImMessageContext messageContext);

    boolean executedAndClose();
}