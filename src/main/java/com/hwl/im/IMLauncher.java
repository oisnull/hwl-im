package com.hwl.im;

import com.hwl.im.client.IMClient;
import com.hwl.im.server.IMServer;
import com.hwl.im.server.redis.store.OfflineMessageStore;
import com.hwl.im.server.redis.store.SessionStore;

public class IMLauncher {

    /**
     * windows : java -jar target\hwl-im-1.0.0-jar-with-dependencies.jar imserver 127.0.0.1 8081 
     * java -jar target\hwl-im-1.0.0-jar-with-dependencies.jar imserver 115.29.179.171 8017
     * java -jar target\hwl-im-1.0.0-jar-with-dependencies.jar imclient 127.0.0.1 8081 3 123456
     */

    public final static String SERVER_FORMAT_PREFIX = "imserver";
    public final static String SERVER_FORMAT_DESCRIPTION = "imserver localhost port";
    public final static int SERVER_FORMAT_LENGTH = 3;

    public final static String CLIENT_FORMAT_PREFIX = "imclient";
    public final static String CLIENT_FORMAT_DESCRIPTION = "imclient localhost port user_id user_token";
    public final static int CLIENT_FORMAT_LENGTH = 5;

    public static void main(String[] args) {
        if (args == null || args.length <= 0) {
            // args = new String[]{"imclient", "127.0.0.1", "8081", "3", "123456"};
            // args = new String[]{"imserver", "127.0.0.1", "8020"};
        }

        String firstCmd = args[0].toLowerCase();
        if (firstCmd.equals(SERVER_FORMAT_PREFIX)) {
            runServer(args[1], Integer.parseInt(args[2]));
        } else if (firstCmd.equals(CLIENT_FORMAT_PREFIX)) {
            runClient(args);
        } else {
            errorDesc();
        }
    }

    static void errorDesc() {
        System.out.println("im init command : ");
        System.out.println(SERVER_FORMAT_DESCRIPTION + " / " + CLIENT_FORMAT_DESCRIPTION);
    }

    static void runServer(String host, int port) {
        IMServer server = new IMServer(host, port);
        server.setOfflineMessageStorage(new OfflineMessageStore());
        server.setOnlineSessionStorage(new SessionStore());
        server.start();
    }

    static void runClient(String[] args) {
        IMClient client = new IMClient(args);
        client.connect();
    }
}
