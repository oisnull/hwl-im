package com.hwl.im.core.imom;

import java.util.function.Consumer;

public interface OnlineSessionStorageMedia {
    String getSession(Long userid);
	
	long getUserId(String sessionid);

    void setSession(Long userid, String sessionid, Consumer<Boolean> operateCallback);

//    void removeSession(Long userid);

    void removeSession(String sessionid);
}