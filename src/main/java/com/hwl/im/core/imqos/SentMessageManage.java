package com.hwl.im.core.imqos;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.hwl.im.core.immode.MessageResponseHeadOperate;
import com.hwl.imcore.improto.ImMessageContext;

public class SentMessageManage {

    public final static int MAX_MESSAGE_COUNT = 10;

    private final static ConcurrentHashMap<Long, LinkedList<ImMessageContext>> sentMessageContainer = new ConcurrentHashMap<>();
    private static SentMessageManage instance = new SentMessageManage();

    public static SentMessageManage getInstance() {
        //if (instance == null)
        //    instance = new SentMessageManage();

        return instance;
    }

    public boolean isSpilled(long userId) {
        return getMessageCount(userId) >= MAX_MESSAGE_COUNT;
    }

    public int getMessageCount(long userId) {
        if (userId <= 0) return MAX_MESSAGE_COUNT;
        LinkedList<ImMessageContext> messages = sentMessageContainer.get(userId);
        if (messages == null) {
            return 0;
        } else {
            return messages.size();
        }
    }

//    public ImMessageContext pollMessage(long userId) {
//        if (userId <= 0)
//            return null;
//
//        LinkedList<ImMessageContext> messages = sentMessageContainer.get(userId);
//        if (messages == null || messages.size() <= 0)
//            return null;
//
//        return messages.poll();
//    }

    public LinkedList<ImMessageContext> getMessages(long userId) {
        if (userId <= 0) return null;

        return sentMessageContainer.get(userId);
    }

    public void addMessage(long userId, ImMessageContext messageContext) {
        if (userId <= 0 || messageContext == null) return;
        if (!MessageResponseHeadOperate.isAck(messageContext)) return;
        String messageid = MessageResponseHeadOperate.getMessageId(messageContext);
        if (messageid == null || messageid == "") return;

        LinkedList<ImMessageContext> messages = sentMessageContainer.get(userId);
        if (messages == null) {
            messages = new LinkedList<>();
            messages.add(messageContext);
            sentMessageContainer.put(userId, messages);
        } else {
            //lock list
            synchronized (messages) {
                messages.add(messageContext);
            }
        }
    }

    public void removeMessage(long userId, String messageGuid) {
        this.removeMessage(userId, messageGuid, null);
    }

    public void removeMessage(long userId, String messageGuid, Runnable emptyCallback) {
        if (userId <= 0 || messageGuid == null || messageGuid == "") {
            if (emptyCallback != null) emptyCallback.run();
            return;
        }

        LinkedList<ImMessageContext> messages = sentMessageContainer.get(userId);
        if (messages == null || messages.size() <= 0) return;

        synchronized (messages) {
            for (int i = 0; i < messages.size(); i++) {
                if (messageGuid.equals(messages.get(i).getResponse().getResponseHead().getMessageid())) {
                    messages.remove(i);
                    break;
                }
            }
        }
        if (messages.size() <= 0 && emptyCallback != null) {
            emptyCallback.run();
        }
    }
}