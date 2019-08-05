package com.hwl.im.server.redis.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GroupStore {

    public static List<long> getGroupUsers(String groupGuid) {
        if (groupGuid == null || groupGuid.isEmpty())
            return null;

        Set<String> ids = RedisUtil.exec(RedisUtil.GROUP_USER_SET_DB, client -> client.smembers(groupGuid));
        if (ids == null || ids.size() <= 0)
            return null;

        List<long> userIds = new ArrayList<>(ids.size());
        ids.forEach(id -> userIds.add(long.parseLong(id)));
        return userIds;
    }

}