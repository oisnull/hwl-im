package com.hwl.im.client.extra;

public interface IClientConnectListener extends IClientChannelListener {

    void onBuildConnectionSuccess(String clientAddress, String serverAddress);

    void onBuildConnectionFailure(String clientAddress,  String errorInfo);

    void onClosed(String clientAddress);

}