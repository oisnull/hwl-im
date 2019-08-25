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
    static Logger log = LogManager.getLogger(ServerSentMessageManager.class.getName());

    public final static int MAX_MESSAGE_COUNT = 10;

    public ServerSentMessageManager(String sourceType) {
        super(sourceType);
    }

    public boolean isSpilled(long userid) {
        return getMessageCount(userid) >= MAX_MESSAGE_COUNT;
    }

    public int getMessageCount(long userid) {
        if (messageContainer.containsKey(userid) && messageContainer.get(userid) != null) {
            return messageContainer.get(userid).size();
        }
        return 0;
    }

    public void removeUser(long userid) {
        if (userid <= 0)
            return;

        if (messageContainer.containsKey(userid)) {
            messageContainer.remove(userid);
        }
    }

    public void removeMessage(long userid, String messageGuid) {
        if (userid <= 0 || messageGuid == null)
            return;

        if (!messageContainer.containsKey(userid) || messageContainer.get(userid) == null)
            return;

        List<ImMessageContext> messages = messageContainer.get(userid);
        messages.removeIf(r -> messageGuid.equals(ImMessageContextParser.getMessageId(r)));

        log.info("Server delete sent message of userid({}) and message({}),current count is {}", userid, messageGuid,
                messages.size());
    }
}