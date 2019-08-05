package com.hwl.im.server.defa;

import com.hwl.im.server.ImCoreCommon;
import com.hwl.im.server.extra.IOnlineSessionStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class DefaultOnlineSessionManager implements IOnlineSessionStorage {
    static Logger log = LogManager.getLogger(DefaultOnlineSessionManager.class.getName());

    protected ConcurrentHashMap<Long, String> onlineSessions;

    public DefaultOnlineSessionManager() {
        onlineSessions = new ConcurrentHashMap<>();
    }

    public String getSession(long userid) {
        if (userid <= 0) {
            log.error("getSession : userid = {}", userid);
            return null;
        }
        return onlineSessions.get(userid);
    }

    public long getUserId(String sessionid) {
        if (sessionid == null || sessionid.trim().equals("")) {
            return 0;
        }
        return ImCoreCommon.getKeyByValue(onlineSessions, sessionid);
    }

    public void removeSession(String sessionid) {
        if (sessionid == null || sessionid.trim().equals("")) {
            log.error("removeSession : sessionid = {}", sessionid);
            return;
        }
        Long userid = ImCoreCommon.getKeyByValue(onlineSessions, sessionid);
        if (userid == null)
            return;
        onlineSessions.remove(userid);
    }

    public void setSession(long userid, String sessionid, Consumer<Boolean> operateCallback) {
        if (userid <= 0 || sessionid == null || sessionid.trim().equals("")) {
            operateCallback.accept(false);
            log.error("setSession : userid = {} , sessionid = {}", userid, sessionid);
            return;
        }
        onlineSessions.put(userid, sessionid);
        operateCallback.accept(true);
    }
}