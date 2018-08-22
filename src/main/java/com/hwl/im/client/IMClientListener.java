package com.hwl.im.client;

public interface IMClientListener {

    void onBuildConnectionSuccess(String clientAddress, String serverAddress);

    void onBuildConnectionError(String clientAddress, String serverAddress, String errorInfo);

    void onClosed(String clientAddress);

    void onDisconnected(String clientAddress);

    void onError(String clientAddress, String errorInfo);

}