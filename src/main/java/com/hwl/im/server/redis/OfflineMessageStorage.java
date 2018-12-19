package com.hwl.im.server.redis;

import java.util.LinkedList;
import java.util.List;

import com.hwl.im.core.imstore.OfflineMessageStorageMedia;
import com.hwl.imcore.improto.ImMessageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OfflineMessageStorage implements OfflineMessageStorageMedia {

    static Logger log = LogManager.getLogger(OfflineMessageStorage.class.getName());

    private static byte[] getStoreKey(Long userid) {
        return String.format("offlinemsg:%d", userid).getBytes();
    }

    @Override
    public void addMessage(long userid, ImMessageContext messageContext) {
        if (userid <= 0 || messageContext == null)
            return;

        log.debug("Server userid({}) is offline and add message to redis", userid);

        RedisUtil.exec(RedisUtil.OFFLINE_MESSAGE_DB,
                client -> client.lpush(getStoreKey(userid), messageContext.toByteArray()));
    }

    @Override
    public void addMessages(long userid, LinkedList<ImMessageContext> messageContexts) {

    }

    @Override
    public List<ImMessageContext> getMessages(long userid) {
        return null;
    }

    @Override
    public ImMessageContext pollMessage(long userid) {
        if (userid <= 0)
            return null;

        byte[] bytes = RedisUtil.exec(RedisUtil.OFFLINE_MESSAGE_DB, client -> client.lpop(getStoreKey(userid)));
        if (bytes == null || bytes.length <= 0)
            return null;

        try {
            return ImMessageContext.parseFrom(bytes);
        } catch (Exception e) {
            log.error("Server offline message convert failed , userid {}", userid);
            e.printStackTrace();
        }

        return null;
    }

}