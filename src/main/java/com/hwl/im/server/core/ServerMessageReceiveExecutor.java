package com.hwl.im.server.core;


public interface ServerMessageReceiveExecutor {

    void setRequestValidator(IRequestValidator validator);

    ImMessageContext execute(ImMessageType messageType, ImMessageRequestHead requestHead, IChannel channel);
}