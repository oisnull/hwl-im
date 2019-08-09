package com.hwl.im.core.imaction;

import com.hwl.imcore.improto.ImMessageContext;

public interface IClientMessageListenExecutor {
    void execute(ImMessageContext messageContext);

    boolean executedAndClose();
}