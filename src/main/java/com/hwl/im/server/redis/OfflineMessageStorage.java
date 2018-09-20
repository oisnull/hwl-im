package com.hwl.im.server.redis;

import java.util.List;

import com.hwl.im.core.imstore.OfflineMessageStorageMedia;
import com.hwl.im.improto.ImMessageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OfflineMessageStorage implements OfflineMessageStorageMedia {

    static Logger log = LogManager.getLogger(OfflineMessageStorage.class.getName());

    private static byte[] getStoreKey(Long userid) {
        return String.format("offmsg:%d", userid).getBytes();
    }

    @Override
    public void addMessage(Long userid, ImMessageContext messageContext) {
        if (userid <= 0 || messageContext == null)
            return;

        log.debug("Server userid({}) is offline and add message to redis , message content : {}", userid,
                messageContext.toString());

        RedisUtil.exec(RedisUtil.OFFLINE_MESSAGE_DB,
                client -> client.lpush(getStoreKey(userid), messageContext.toByteArray()));
    }

    @Override
    public List<ImMessageContext> getMessages(Long userid) {
        return null;
    }

    @Override
    public ImMessageContext pollMessage(Long userid) {
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