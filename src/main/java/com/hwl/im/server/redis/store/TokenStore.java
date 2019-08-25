package com.hwl.im.server.redis.store;

import com.hwl.im.server.redis.RedisUtil;

public class TokenStore {
    public static String getUserToken(long userId) {
        if (userId <= 0)
            return null;

        return RedisUtil.exec(RedisUtil.USER_TOKEN_DB, client -> client.get(String.valueOf(userId)));
    }
}
