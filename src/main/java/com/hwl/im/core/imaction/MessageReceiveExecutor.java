package com.hwl.im.core.imaction;

import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageRequestHead;
import com.hwl.imcore.improto.ImMessageType;

import io.netty.channel.Channel;

public interface MessageReceiveExecutor {

    ImMessageType getMessageType();

    void setRequestHead(ImMessageRequestHead requestHead);

    void setChannel(Channel channel);

    ImMessageContext execute();
}
