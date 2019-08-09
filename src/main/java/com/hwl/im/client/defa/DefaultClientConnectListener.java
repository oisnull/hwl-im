package com.hwl.im.client.defa;

import com.hwl.im.client.extra.IClientConnectListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultClientConnectListener implements IClientConnectListener {
    static Logger log = LogManager.getLogger(DefaultClientConnectListener.class.getName());

    public void onBuildConnectionFailure(String clientAddress, String errorInfo) {
        log.info("onBuildConnectionFailure : {},{}", clientAddress, errorInfo);
    }

    public void onBuildConnectionSuccess(String clientAddress, String serverAddress) {
        log.info("onBuildConnectionSuccess : {},{}", clientAddress, serverAddress);
    }

    public void onClosed(String clientAddress) {
        log.info("onClosed : " + clientAddress);
    }

    public void onConnected(String clientAddress, String serverAddress) {
        log.info("onConnected : {},{}", clientAddress, serverAddress);
    }

    public void onDisconnected(String clientAddress) {
        log.info("onDisconnected : " + clientAddress);
    }

    public void onError(String clientAddress, String errorInfo) {
        log.info("onError : {},{}", clientAddress, errorInfo);
    }
}


//private class DefaultClientListener implements IMClientListener {
//
//    @Override
//    public void onBuildConnectionSuccess(String clientAddress, String serverAddress) {
//        log.info("Client {} connected to server {} successfully.", clientAddress, serverAddress);
//    }
//
//    @Override
//    public void onBuildConnectionError(String clientAddress, String serverAddress, String errorInfo) {
//        log.info("Client {} connected to server {} failure. info :", clientAddress, serverAddress, errorInfo);
//    }
//
//    @Override
//    public void onClosed(String clientAddress) {
//        log.info("Client {} closed", clientAddress);
//    }
//
//    @Override
//    public void onDisconnected(String clientAddress) {
//        log.info("Client {} disconnect", clientAddress);
//    }
//
//    @Override
//    public void onError(String clientAddress, String errorInfo) {
//        log.info("An error occurred on the client {}. info : {}", clientAddress, errorInfo);
//    }
//
//}