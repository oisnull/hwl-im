package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceivExecutor;
import com.hwl.im.core.imom.OnlineManage;
import com.hwl.imcore.improto.ImHeartBeatMessageRequest;
import com.hwl.imcore.improto.ImMessageType;
import com.hwl.imcore.improto.ImMessageResponse.Builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeartBeatMessageReceiveExecutor extends AbstractMessageReceivExecutor<ImHeartBeatMessageRequest> {

    static Logger log = LogManager.getLogger(HeartBeatMessageReceiveExecutor.class.getName());

    public HeartBeatMessageReceiveExecutor(ImHeartBeatMessageRequest imHeartBeatMessageRequest) {
        super(imHeartBeatMessageRequest);
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.HeartBeat;
    }

    @Override
    public void executeCore(Builder response) {
        if (!OnlineManage.getInstance().isOnline(requestHead.getSessionid())) {
            log.debug("Server heartbeat : user is offline , client will be close!");
            channel.close();
        } else {
            log.debug("Server heartbeat : user is online , {}", request.getCurrentTime());
        }
    }
}
