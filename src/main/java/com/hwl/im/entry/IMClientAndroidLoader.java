package com.hwl.im.entry;

import com.hwl.im.client.IMClientListener;
import com.hwl.im.entry.IMClientAndroidEntry.DefaultSendOperateListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IMClientAndroidLoader {

    /**
     * Process : 1,(app start|app disconnect network and reconnect) and connect im
     * server 2,connect im server success and send user validate message
     * 
     * 
     * Function list :
     * 
     * 
     */

    static Logger log = LogManager.getLogger(IMClientAndroidLoader.class.getName());
    static Long userId = 10000L;
    static String userToken = "123456";

    public static void autoConnect() {
        log.debug("Im loader auto connect {} run ...", IMClientAndroidEntry.getServerAddress());
        IMClientAndroidEntry.registerClientListener(clientListener);
        IMClientAndroidEntry.connectServer();
    }

    static DefaultSendOperateListener operateListener = new DefaultSendOperateListener() {
        @Override
        public void success() {
            // nothing do it
        }

        public void unconnect() {
            log.debug("Android client send operate : unconnect server ");
        }

        public void notSendToServer() {
            log.debug("Android client send operate : message not send to server ");
        }

        public void failed(String message) {
            // call ui for android
            log.debug("Android client send operate failed : {}", message);
        }

        public void sessionidInvaild() {
            log.debug("Android client send operate : sessionid invalid");
        }
    };

    static IMClientListener clientListener = new IMClientListener() {
        @Override
        public void onBuildConnectionSuccess(String clientAddress, String serverAddress) {
            log.debug("Client {} connected to server {} successfully.", clientAddress, serverAddress);
            IMClientAndroidEntry.stopCheckConnect();
            IMClientAndroidEntry.sendUserValidateMessage(userId, userToken, operateListener);
        }

        @Override
        public void onBuildConnectionError(String clientAddress, String serverAddress, String errorInfo) {
            log.error("Client {} connected to server {} failure. info :", clientAddress, serverAddress, errorInfo);
            IMClientAndroidEntry.startCheckConnect();
        }

        @Override
        public void onClosed(String clientAddress) {
            log.debug("Client {} closed", clientAddress);
            IMClientAndroidEntry.startCheckConnect();
        }

        @Override
        public void onDisconnected(String clientAddress) {
            log.debug("Client {} disconnect", clientAddress);
            IMClientAndroidEntry.startCheckConnect();
        }

        @Override
        public void onError(String clientAddress, String errorInfo) {
            log.error("An error occurred on the client {}. info : {}", clientAddress, errorInfo);
            IMClientAndroidEntry.startCheckConnect();
        }
    };

}