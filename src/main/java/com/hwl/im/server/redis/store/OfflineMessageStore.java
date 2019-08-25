package com.hwl.im.server.redis.store;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hwl.im.server.extra.IOfflineMessageStorage;
import com.hwl.im.server.redis.RedisUtil;
import com.hwl.imcore.improto.ImMessageContext;

public class OfflineMessageStore implements IOfflineMessageStorage {

    private static byte[] getStoreKey(Long userid) {
        return String.format("offlinemsg:%d", userid).getBytes();
    }

    @Override
    public void addFirst(long userid, ImMessageContext messageContext) {
        if (userid <= 0 || messageContext == null)
            return;

        RedisUtil.exec(RedisUtil.OFFLINE_MESSAGE_DB,
                client -> client.lpush(getStoreKey(userid), messageContext.toByteArray()));
    }

    @Override
    public void addMessage(long userid, ImMessageContext messageContext) {
        if (userid <= 0 || messageContext == null)
            return;

        RedisUtil.exec(RedisUtil.OFFLINE_MESSAGE_DB,
                client -> client.rpush(getStoreKey(userid), messageContext.toByteArray()));
    }

    @Override
    public void addMessages(long userid, List<ImMessageContext> messageContexts) {
        if (userid <= 0 || messageContexts == null || messageContexts.size() <= 0)
            return;

        RedisUtil.exec(RedisUtil.OFFLINE_MESSAGE_DB, client -> {
            int msgCount = messageContexts.size() - 1;
            for (int i = msgCount; i >= 0; i--) {
                client.rpush(getStoreKey(userid), messageContexts.get(i).toByteArray());
            }
            return null;
        });
    }

    @Override
    public List<ImMessageContext> getMessages(long userid) {
        List<byte[]> messages = RedisUtil.exec(RedisUtil.OFFLINE_MESSAGE_DB,
                client -> client.lrange(getStoreKey(userid), 0, -1));
        if (messages == null || messages.size() <= 0)
            return null;

        List<ImMessageContext> results = new ArrayList<>(messages.size());
        for (byte[] item : messages) {
            try {
                results.add(ImMessageContext.parseFrom(item));
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
        return results;
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
            e.printStackTrace();
        }

        return null;
    }
}