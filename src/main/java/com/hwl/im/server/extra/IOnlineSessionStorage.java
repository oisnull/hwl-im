package com.hwl.im.server.extra;

import java.util.function.Consumer;

public interface IOnlineSessionStorage {
    String getSession(long userid);
	
	long getUserId(String sessionid);

    void setSession(long userid, String sessionid, Consumer<Boolean> operateCallback);

    void removeSession(String sessionid);
}