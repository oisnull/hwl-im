package com.hwl.im.server.default;

import com.hwl.imcore.improto.ImMessageRequestHead;

public class DefaultOnlineSessionManager implements IOnlineSessionStorage
{
    static Logger log = LogManager.getLogger(DefaultOnlineSessionManager.class.getName());

	protected ConcurrentHashMap<Long, String> onlineSessions;
    public DefaultOnlineSessionManager()
    {
        onlineSessions = new ConcurrentHashMap<>();
    }

    public String getSession(long userid)
    {
        if (userid <= 0) {
            log.error("getSession : userid = {}", userid);
            return null;
        }
        return onlineSessions.get(userid);
    }

    public long getUserId(String sessionid)
    {
        if (sessionid == null || sessionid.trim().equals("")) {
            return 0;
        }
        return ImCoreCommon.getKeyByValue(onlineSessions, sessionid);
    }

    public void removeSession(String sessionid)
    {
        if (sessionid == null || sessionid.trim().equals("")) {
            log.error("removeSession : sessionid = {}", sessionid);
            return;
        }
        Long userid = ImCoreCommon.getKeyByValue(onlineSessions, sessionid);
        if (userid == null)
            return;
        onlineSessions.remove(userid);
    }

    public void setSession(long userid, String sessionid, Consumer<bool> operateCallback)
    {
        if (userid <= 0 || sessionid == null || sessionid.trim().equals("")) {
            operateCallback.accept(false);
            log.error("setSession : userid = {} , sessionid = {}", userid, sessionid);
            return;
        }
        onlineSessions.put(userid, sessionid);
        operateCallback.accept(true);
    }
}