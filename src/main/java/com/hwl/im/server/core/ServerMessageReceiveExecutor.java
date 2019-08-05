package com.hwl.im.server.core;

import com.hwl.im.server.extra.IRequestValidator;
import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageRequestHead;
import com.hwl.imcore.improto.ImMessageType;

import io.netty.channel.Channel;

public interface ServerMessageReceiveExecutor {

    void setRequestValidator(IRequestValidator validator);

    ImMessageContext execute(ImMessageType messageType, ImMessageRequestHead requestHead, Channel channel);
}