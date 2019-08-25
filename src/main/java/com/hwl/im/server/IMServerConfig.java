package com.hwl.im.server;

public class IMServerConfig {
    public final static int IDLE_TIMEOUT_SECONDS = 3 * 60;

    public final static String REDIS_USER_HOSTS = "127.0.0.1:6379,allowadmin=true,password=123456";
    public final static String REDIS_MESSAGE_HOSTS = "127.0.0.1:8379,allowadmin=true,password=123456";
	
    // store db , db config from c# redis config
    public final static int USER_TOKEN_DB = 0;
    public final static int USER_SESSION_DB = 2;
    public final static int GROUP_USER_SET_DB = 11;
    public final static int OFFLINE_MESSAGE_DB = 9;
}