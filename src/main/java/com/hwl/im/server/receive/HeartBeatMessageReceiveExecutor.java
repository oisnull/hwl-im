package com.hwl.im.server.receive;

import com.hwl.im.server.action.OnlineChannelManager;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.imcore.improto.ImHeartBeatMessageRequest;
import com.hwl.imcore.improto.ImMessageResponse.Builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeartBeatMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImHeartBeatMessageRequest> {

    static Logger log = LogManager.getLogger(HeartBeatMessageReceiveExecutor.class.getName());

    public HeartBeatMessageReceiveExecutor(ImHeartBeatMessageRequest imHeartBeatMessageRequest) {
        super(imHeartBeatMessageRequest);
    }

    @Override
    public void executeCore(Builder response) {
        if (!OnlineChannelManager.getInstance().isOnline(requestHead.getSessionid())) {
            log.debug("Server heartbeat : user {} is offline , client will be close!", request.getFromUserId());
            channel.close();
        } else {
            log.debug("Server heartbeat : user {} is online , {}", request.getFromUserId(), request.getCurrentTime());
        }
    }
}
