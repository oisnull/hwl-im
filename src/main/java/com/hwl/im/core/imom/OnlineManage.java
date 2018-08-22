package com.hwl.im.core.imom;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class OnlineManage {

    public final static String USER_SESSION_IDENTITY = "user-session-identity";
    public final static AttributeKey<String> USER_SESSION_IDENTITY_ATTR = AttributeKey.valueOf(USER_SESSION_IDENTITY);

    private final static ConcurrentHashMap<String, Channel> onlineChannels = new ConcurrentHashMap<String, Channel>();
    private static OnlineManage instance = null;
    private static Logger log = LogManager.getLogger(OnlineManage.class.getName());
    private static OnlineSessionStorageMedia sessionManager = new OnlineSessionMemoryManage();
    private static boolean isDeugger = true;

    private OnlineManage() {
    }

    public static OnlineManage getInstance() {
        if (instance == null)
            instance = new OnlineManage();

        return instance;
    }

    public static void setSessionStorageMedia(OnlineSessionStorageMedia sessionStorageMedia) {
        if (sessionStorageMedia != null) {
            sessionManager = sessionStorageMedia;
        }
    }

    public void printLog() {
        if (!isDeugger)
            return;

        log.debug("Online users : {}", onlineChannels.size());

        if (onlineChannels.size() <= 0)
            return;

        for (String key : onlineChannels.keySet())
            log.debug(" sessionid : {} ,address : {}", key, onlineChannels.get(key).remoteAddress());
    }

    public Channel getChannel(String sessionid) {
        if (sessionid == null || sessionid.isEmpty())
            return null;
        return onlineChannels.get(sessionid);
    }

    public void addChannel(Channel channel) {
        String key = channel.attr(USER_SESSION_IDENTITY_ATTR).get();

        onlineChannels.put(key, channel);

        printLog();
    }

    public void removeChannel(Channel channel) {
        synchronized (onlineChannels) {
            String key = channel.attr(USER_SESSION_IDENTITY_ATTR).get();
            log.debug("Remove channel : key is {}", key);
            if (key != null) {
                sessionManager.removeSession(key);
                onlineChannels.remove(key);
            }
        }

        printLog();
    }

    public String getSession(Long userid) {
        if (userid <= 0)
            return null;
        return sessionManager.getSession(userid);
    }

    public boolean isOnline(Long userid) {
        if (userid <= 0)
            return false;

        return sessionManager.getSession(userid) != null;
    }

    public boolean isOnline(String sessionid) {
        if (sessionid == null || sessionid.isEmpty())
            return false;
        return onlineChannels.containsKey(sessionid);
    }

    public Channel getChannel(Long userid) {
        if (userid <= 0)
            return null;

        String sessid = sessionManager.getSession(userid);
        if (sessid == null || sessid.isEmpty()) {
            return null;
        }

        return onlineChannels.get(sessid);
    }

    public void setChannelSessionid(Long userid, String sessionid, Channel channel) {
        if (userid <= 0 || sessionid == null || sessionid.isEmpty() || channel == null) {
            log.error("setChannelSessionid : userid = {} , sessionid = {} , channel = {}", userid, sessionid,
                    channel.remoteAddress());
            return;
        }
        sessionManager.setSession(userid, sessionid);
        channel.attr(USER_SESSION_IDENTITY_ATTR).set(sessionid);
        onlineChannels.put(sessionid, channel);
    }
}