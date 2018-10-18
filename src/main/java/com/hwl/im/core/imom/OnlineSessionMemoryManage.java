package com.hwl.im.core.imom;

import java.util.concurrent.ConcurrentHashMap;

import com.hwl.im.core.ImCoreCommon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OnlineSessionMemoryManage implements OnlineSessionStorageMedia {

    final static ConcurrentHashMap<Long, String> onlineSessions = new ConcurrentHashMap<>();
    static Logger log = LogManager.getLogger(OnlineSessionMemoryManage.class.getName());

    @Override
    public String getSession(Long userid) {
        if (userid <= 0) {
            log.error("getSession : userid = {}", userid);
            return null;
        }
        return onlineSessions.get(userid);
    }

    @Override
    public void setSession(Long userid, String sessionid) {
        if (userid <= 0 || sessionid == null || sessionid.trim().equals("")) {
            log.error("setSession : userid = {} , sessionid = {}", userid, sessionid);
            return;
        }
        onlineSessions.put(userid, sessionid);
    }

//    @Override
//    public void removeSession(Long userid) {
//        if (userid <= 0) {
//            log.error("removeSession : userid = {}", userid);
//            return;
//        }
//        onlineSessions.remove(userid);
//    }

    @Override
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
}