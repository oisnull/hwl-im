package com.hwl.im.core.imstore;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import com.hwl.im.core.proto.ImMessageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OfflineMessageManage {

    /**
     * <userid,responseContext>
     */
    final static ConcurrentHashMap<Long, Queue<ImMessageContext>> offlineMessages = new ConcurrentHashMap<>();
    static Logger log = LogManager.getLogger(OfflineMessageManage.class.getName());

    private static OfflineMessageManage instance = null;
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

    public void addMessage(Long userid, ImMessageContext messageContext) {
        if (userid <= 0 || messageContext == null) {
            return;
        }
        messageManager.addMessage(userid, messageContext);
    }

    public List<ImMessageContext> getMessages(Long userid) {
        return messageManager.getMessages(userid);
    }

    public ImMessageContext pollMessage(Long userid) {
        return messageManager.pollMessage(userid);
    }
}