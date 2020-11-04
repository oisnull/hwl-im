package com.hwl.im.server.redis.store;

import com.hwl.im.server.extra.IOnlineSessionStorage;

import com.hwl.im.server.redis.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class SessionStore implements IOnlineSessionStorage {

    static Logger log = LogManager.getLogger(SessionStore.class.getName());
    static final String USER_SESSION_OFFLINE_DETAILS_KEY = "offline:user:details";

	private void removeUserOfflineStatus(String useridKey){
        RedisUtil.exec(RedisUtil.USER_SESSION_OFFLINE_DB, client -> {
            client.hdel(USER_SESSION_OFFLINE_DETAILS_KEY, useridKey);
            return null;
        });		
	}

	private void addUserOfflineStatus(String useridKey){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date todayNow = new Date();
		String value = sdf.format(todayNow);
        RedisUtil.exec(RedisUtil.USER_SESSION_OFFLINE_DB, client -> {
            client.hset(USER_SESSION_OFFLINE_DETAILS_KEY, useridKey, value);
            return null;
        });		
	}

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

		String useridKey = String.valueOf(userid);
        RedisUtil.exec(RedisUtil.USER_SESSION_DB, client -> {
            Transaction tran = client.multi();
            try {
                tran.set(useridKey, sessionid);
                tran.set(sessionid, useridKey);
                tran.exec();
				//remove offline status from user offline db
				removeUserOfflineStatus(useridKey);
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
				String useridKey = String.valueOf(userid);
                Transaction tran = client.multi();
                try {
                    tran.del(useridKey);
                    tran.del(sessionid);
                    tran.exec();
					//add user info into offline db and user's nearby data will be removed after 2 hours
					addUserOfflineStatus(useridKey);
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