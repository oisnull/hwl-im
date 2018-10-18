package com.hwl.im.server.redis;

import com.hwl.im.core.imom.OnlineSessionStorageMedia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Transaction;

public class SessionStorage implements OnlineSessionStorageMedia {

    static Logger log = LogManager.getLogger(SessionStorage.class.getName());

    @Override
    public String getSession(Long userid) {
        if (userid <= 0)
            return null;

        return RedisUtil.exec(RedisUtil.USER_SESSION_DB, client -> client.get(String.valueOf(userid)));
    }

    @Override
    public void setSession(Long userid, String sessionid) {
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
                log.debug("Set user session success , userid:{} ,sessionid:{}", userid, sessionid);
            } catch (Exception e) {
                tran.discard();
                log.debug("Set user session failed , userid:{} ,sessionid:{} , error info : {}", userid, sessionid,
                        e.getMessage());
            }
            return null;
        });
    }

//    @Override
//    public void removeSession(Long userid) {
//        if (userid <= 0)
//            return;
//
//        RedisUtil.exec(RedisUtil.USER_SESSION_DB, client -> {
//            String sessionid = client.get(String.valueOf(userid));
//            if (sessionid != null && !sessionid.isEmpty()) {
//                Transaction tran = client.multi();
//                try {
//                    tran.del(String.valueOf(userid));
//                    tran.del(sessionid);
//                    tran.exec();
//                    log.debug("Remove user session success , userid:{} ,sessionid:{}", userid, sessionid);
//                } catch (Exception e) {
//                    tran.discard();
//                    log.debug("Remove user session failed , userid:{} ,sessionid:{} , error info : {}", userid,
//                            sessionid, e.getMessage());
//                }
//            }
//
//            return null;
//        });
//    }

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