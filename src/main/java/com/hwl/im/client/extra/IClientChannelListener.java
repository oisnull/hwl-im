package com.hwl.im.client.extra;

public interface IClientChannelListener {

    void onConnected(String clientAddress, String serverAddress);

    void onDisconnected(String clientAddress);

    void onError(String clientAddress, String errorInfo);

}