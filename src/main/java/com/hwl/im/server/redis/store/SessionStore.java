package com.hwl.im.server.redis.store;

import com.hwl.im.server.extra.IOnlineSessionStorage;

import com.hwl.im.server.redis.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Transaction;

import java.util.function.Consumer;

public class SessionStore implements IOnlineSessionStorage {

    static Logger log = LogManager.getLogger(SessionStore.class.getName());

    @Override
    public String getSession(long userid) {
        if (userid <= 0)
            return null;

        return RedisUtil.exec(RedisUtil.USER_SESSION_DB, client -> client.get(String.valueOf(userid)));
    }

    @Override
    public long getUserId(String sessionid) {
        if (sessionid == null || sessionid.isEmpty())
            return 0;

        return RedisUtil.exec(RedisUtil.USER_SESSION_DB, client -> Long.parseLong(client.get(sessionid)));
    }

    @Override
    public void setSession(long userid, String sessionid, Consumer<Boolean> operateCallback) {
        if (userid <= 0)
            return;
        if (sessionid == null || sessionid.isEmpty())
            return;

        RedisUtil.exec(RedisUtil.USER_SESSION_DB, client -> {
            Transaction tran = client.multi();
            try {
                tran.set(String.valueOf(userid), sessionid);
                tran.set(sessionid, String.valueOf(userid));
                tran.exec();
                operateCallback.accept(true);
                log.debug("Set user session success , userid:{} ,sessionid:{}", userid, sessionid);
            } catch (Exception e) {
                tran.discard();
                operateCallback.accept(false);
                log.debug("Set user session failed , userid:{} ,sessionid:{} , error info : {}", userid, sessionid,
                        e.getMessage());
            }
            return null;
        });
    }

    @Override
    public void removeSession(String sessionid) {
        if (sessionid == null || sessionid.isEmpty())
            return;

        RedisUtil.exec(RedisUtil.USER_SESSION_DB, client -> {
            Long userid = Long.parseLong(client.get(sessionid));
            if (userid > 0) {
                Transaction tran = client.multi();
                try {
                    tran.del(String.valueOf(userid));
                    tran.del(sessionid);
                    tran.exec();
                    log.debug("Remove user session success , userid:{} ,sessionid:{}", userid, sessionid);
                } catch (Exception e) {
                    tran.discard();
                    log.debug("Remove user session failed , userid:{} ,sessionid:{} , error info : {}", userid,
                            sessionid, e.getMessage());
                }
            }

            return null;
        });
    }
}