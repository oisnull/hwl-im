package com.hwl.im.server.action;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.hwl.im.server.defa.DefaultOfflineMessageManager;
import com.hwl.imcore.improto.ImMessageContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class ServerSentMessageManager extends DefaultOfflineMessageManager {

    public final static int MAX_MESSAGE_COUNT = 10;

    public ServerSentMessageManager(String sourceType) {
        super(sourceType);
    }

    public boolean isSpilled(long userId) {
        return getMessageCount(userId) >= MAX_MESSAGE_COUNT;
    }

    public int getMessageCount(long userId) {
        if (userId <= 0) return MAX_MESSAGE_COUNT;

//        List<ImMessageContext> messages = messageContainer[userId];
//        if (messages == null) {
//            return 0;
//        } else {
//            return messages.Count;
//        }
        return 0;
    }

    public void removeUser(long userId) {
        if (userId <= 0) return;

        //if (getMessageCount(userId) > 0) return;

//        messageContainer.Remove(userId);
    }

    public void removeMessage(long userId, String messageGuid) {
//        if (userId <= 0 || string.IsNullOrEmpty(messageGuid))
//            return;
//
//        List<ImMessageContext> messages = messageContainer[userId];
//        if (messages == null || messages.Count <= 0) return;
//
//        messages.RemoveAll(r = > r.Response ?.ResponseHead ?.Messageid == messageGuid);
    }
}