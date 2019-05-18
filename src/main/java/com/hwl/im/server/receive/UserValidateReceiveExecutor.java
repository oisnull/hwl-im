package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceiveExecutor;
import com.hwl.im.core.immode.MessageOperate;
import com.hwl.im.core.imom.OnlineManage;
import com.hwl.im.server.redis.TokenStorage;
import com.hwl.imcore.improto.ImMessageType;
import com.hwl.imcore.improto.ImUserValidateRequest;
import com.hwl.imcore.improto.ImUserValidateResponse;
import com.hwl.imcore.improto.ImMessageResponse.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.function.Consumer;

public class UserValidateReceiveExecutor extends AbstractMessageReceiveExecutor<ImUserValidateRequest> {
//    private static Logger log = LogManager.getLogger(UserValidateReceiveExecutor.class.getName());

    public UserValidateReceiveExecutor(ImUserValidateRequest request) {
        super(request);
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getUserId() <= 0)
            throw new NullPointerException("user id con not be empty");
        if (request.getToken() == null || request.getToken().trim().equals(""))
            throw new NullPointerException("token con not be empty");
    }

    @Override
    public ImMessageType getMessageType() {
        return ImMessageType.UserValidate;
    }

    @Override
    public boolean isCheckSessionid() {
        return false;
    }

    @Override
    public boolean isResponseNull() {
        return false;
    }

    @Override
    public void executeCore(Builder response) {
//        log.info("Start execute core.");

        if (!checkUserInfo()) {
            response.setUserValidateResponse(
                    ImUserValidateResponse.newBuilder().setIsSuccess(false).setMessage("User id or token is invalid").build());
            return;
        }

        // get user is online or not by userid
        String sessionid = OnlineManage.getInstance().getSession(request.getUserId());
        if (sessionid != null && !sessionid.isEmpty()) {
            //if user is online
            //send message to online user
        }

        final String newSessionid = UUID.randomUUID().toString().replace("-", "");
//        log.info("Set new session id is {}.", newSessionid);
        OnlineManage.getInstance().setChannelSessionid(request.getUserId(), newSessionid, channel, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean succ) {
//                log.info("Set session id {}.", succ.toString());
                if (succ) {
                    response.setUserValidateResponse(
                            ImUserValidateResponse.newBuilder().setIsSuccess(true).setIsOnline(false).setSessionid(newSessionid).build());

//                    log.info("Start offline message push process for user id ({})", request.getUserId());
                    // start offline message push process
                    MessageOperate.serverPushOffline(request.getUserId(), request.getMessageid());
                } else {
                    response.setUserValidateResponse(
                            ImUserValidateResponse.newBuilder().setIsSuccess(false).setMessage("set session failed").setIsOnline(false).setSessionid(newSessionid).build());
                }
            }
        });
    }

    private boolean checkUserInfo() {
        boolean flag = request.getToken().equals(TokenStorage.getUserToken(request.getUserId()));
//        log.info("Check user info {}.", flag);
        return flag;
    }
}