package com.hwl.im.core.imaction;

import com.hwl.im.improto.ImMessageContext;
import com.hwl.im.improto.ImMessageRequestHead;
import com.hwl.im.improto.ImMessageType;

import io.netty.channel.Channel;

public interface MessageReceiveExecutor {

    ImMessageType getMessageType();

    void setRequestHead(ImMessageRequestHead requestHead);

    void setChannel(Channel channel);

    ImMessageContext execute();
}
