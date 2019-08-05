package com.hwl.im.server.redis.store;

import com.hwl.im.server.redis.RedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GroupStore {

    public static List<Long> getGroupUsers(String groupGuid) {
        if (groupGuid == null || groupGuid.isEmpty())
            return null;

        Set<String> ids = RedisUtil.exec(RedisUtil.GROUP_USER_SET_DB, client -> client.smembers(groupGuid));
        if (ids == null || ids.size() <= 0)
            return null;

        List<Long> userIds = new ArrayList<>(ids.size());
        ids.forEach(id -> userIds.add(Long.parseLong(id)));
        return userIds;
    }

}