package com.hwl.im.core.imqos;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.hwl.imcore.improto.ImMessageContext;

public class SentMessageManage {

    public final static int MAX_MESSAGE_COUNT = 10;

    private final static ConcurrentHashMap<Long, LinkedList<ImMessageContext>> sentMessageContainer = new ConcurrentHashMap<>();
    private static SentMessageManage instance = new SentMessageManage();

    private MessageOperateListener operateListener;

    public static SentMessageManage getInstance() {
        //if (instance == null)
        //    instance = new SentMessageManage();

        return instance;
    }

    public void setMessageOperateListener(MessageOperateListener operateListener) {
        this.operateListener = operateListener;
    }

    public void addMessage(long userId, ImMessageContext messageContext) {
        if (userId <= 0 || messageContext == null) return;

        LinkedList<ImMessageContext> messages = sentMessageContainer.get(userId);
        if (messages == null) {
            messages = new LinkedList<>();
            messages.add(messageContext);
            sentMessageContainer.put(userId, messages);
        } else {
            if (messages.size() >= MAX_MESSAGE_COUNT) {
                this.operateListener.onMessageMaximized(userId, messageContext);
            } else {
                //lock list
                messages.add(messageContext);
            }
        }
    }

    public void removeMessage(long userId, String messageGuid) {
        if (userId <= 0 || messageGuid == null || messageGuid == "") return;

        LinkedList<ImMessageContext> messages = sentMessageContainer.get(userId);
        if (messages == null || messages.size() <= 0) return;

        for (int i = 0; i < messages.size(); i++) {
            if (messageGuid.equals(messages.get(i).getRequest().getRequestHead().getMessageid())) {
                messages.remove(i);
                return;
            }
        }
    }

    public interface MessageOperateListener {
        void onMessageMaximized(long userId, ImMessageContext messageContext);

        void onLastMessageSent(long userId);
    }
}