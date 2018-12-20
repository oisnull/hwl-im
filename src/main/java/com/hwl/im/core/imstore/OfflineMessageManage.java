package com.hwl.im.core.imstore;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import com.hwl.imcore.improto.ImMessageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OfflineMessageManage {

    /**
     * <userid,responseContext>
     */
    final static ConcurrentHashMap<Long, Queue<ImMessageContext>> offlineMessages = new ConcurrentHashMap<>();
    static Logger log = LogManager.getLogger(OfflineMessageManage.class.getName());

    private static OfflineMessageManage instance = new OfflineMessageManage();
    private static OfflineMessageStorageMedia messageManager = new OfflineMessageMemoryManage();

    private OfflineMessageManage() {
    }

    public static OfflineMessageManage getInstance() {
        if (instance == null)
            instance = new OfflineMessageManage();

        return instance;
    }

    public static void setOfflineMessageStorageMedia(OfflineMessageStorageMedia offlineMessageStorageMedia) {
        if (messageManager != null) {
            messageManager = offlineMessageStorageMedia;
        }
    }

    public void addMessage(long userid, ImMessageContext messageContext) {
        if (userid <= 0 || messageContext == null) {
            return;
        }
        messageManager.addMessage(userid, messageContext);
    }

    public void addMessages(long userid, LinkedList<ImMessageContext> messageContexts) {
        messageManager.addMessages(userid, messageContexts);
    }

    public List<ImMessageContext> getMessages(long userid) {
        return messageManager.getMessages(userid);
    }

    public ImMessageContext pollMessage(long userid) {
        return messageManager.pollMessage(userid);
    }
}