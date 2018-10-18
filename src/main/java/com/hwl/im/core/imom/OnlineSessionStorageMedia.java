package com.hwl.im.core.imom;

public interface OnlineSessionStorageMedia {
    String getSession(Long userid);

    void setSession(Long userid, String sessionid);

//    void removeSession(Long userid);

    void removeSession(String sessionid);
}