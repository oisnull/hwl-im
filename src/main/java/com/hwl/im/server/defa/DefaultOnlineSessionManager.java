package com.hwl.im.server.defa;

import com.hwl.im.server.ImCoreCommon;
import com.hwl.im.server.extra.IOnlineSessionStorage;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class DefaultOnlineSessionManager implements IOnlineSessionStorage {
    protected ConcurrentHashMap<Long, String> onlineSessions;

    public DefaultOnlineSessionManager() {
        onlineSessions = new ConcurrentHashMap<>();
    }

    public String getSession(long userid) {
        if (userid <= 0) {
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
            return;
        }
        onlineSessions.put(userid, sessionid);
        operateCallback.accept(true);
    }
}