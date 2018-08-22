package com.hwl.im.core.imaction;

import com.hwl.im.core.proto.ImMessageContext;
import com.hwl.im.core.proto.ImMessageRequestHead;
import com.hwl.im.core.proto.ImMessageType;

import io.netty.channel.Channel;

public interface MessageReceiveExecutor {

    ImMessageType getMessageType();

    void setRequestHead(ImMessageRequestHead requestHead);

    void setChannel(Channel channel);

    ImMessageContext execute();
}
