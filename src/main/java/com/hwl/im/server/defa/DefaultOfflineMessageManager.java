package com.hwl.im.server.defa;

import com.hwl.im.server.action.ImMessageContextParser;
import com.hwl.im.server.extra.IOfflineMessageStorage;
import com.hwl.imcore.improto.ImMessageContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultOfflineMessageManager implements IOfflineMessageStorage {
    static Logger log = LogManager.getLogger(DefaultOfflineMessageManager.class.getName());

    protected ConcurrentHashMap<Long, LinkedList<ImMessageContext>> messageContainer;
    private String _sourceType;

    public DefaultOfflineMessageManager(String sourceType) {
        messageContainer = new ConcurrentHashMap<>();
        _sourceType = sourceType;// "temp" : "offline";
    }

    public void addFirst(long userid, ImMessageContext messageContext) {
        if (userid <= 0 || messageContext == null)
            return;

        LinkedList<ImMessageContext> messages = null;
        if (!messageContainer.containsKey(userid)) {
            messages = new LinkedList<ImMessageContext>();
            messageContainer.put(userid, messages);
        } else {
            messages = messageContainer.get(userid);
        }
        messages.add(0, messageContext);

        log.info("Server add userid({}) default {} message({}) to head,current count is {}", userid, _sourceType,
                ImMessageContextParser.getMessageId(messageContext), messages.size());
    }

    public void addMessage(long userid, ImMessageContext messageContext) {
        if (userid <= 0 || messageContext == null)
            return;

        LinkedList<ImMessageContext> messages = null;
        if (!messageContainer.containsKey(userid)) {
            messages = new LinkedList<ImMessageContext>();
            messageContainer.put(userid, messages);
        } else {
            messages = messageContainer.get(userid);
        }
        messages.add(messageContext);

        log.info("Server add userid({}) default {} message({}) to end,current count is {}", userid, _sourceType,
                ImMessageContextParser.getMessageId(messageContext), messages.size());
    }

    public void addMessages(long userid, List<ImMessageContext> messageContexts) {
        if (userid <= 0 || messageContexts == null || messageContexts.size() <= 0)
            return;

        LinkedList<ImMessageContext> messages = null;
        if (!messageContainer.containsKey(userid)) {
            messages = new LinkedList<ImMessageContext>();
            messageContainer.put(userid, messages);
        } else {
            messages = messageContainer.get(userid);
        }

        messages.addAll(messageContexts);

        log.info("Server add userid({}) default {} messages({}) to end,current count is {}", userid, _sourceType,
                messageContexts.size(), messages.size());
    }

    public List<ImMessageContext> getMessages(long userid) {
        return messageContainer.get(userid);
    }

    public ImMessageContext pollMessage(long userid) {
        if (messageContainer.containsKey(userid) && messageContainer.get(userid) != null) {
            return messageContainer.get(userid).poll();
        } else {
            return null;
        }
    }
}