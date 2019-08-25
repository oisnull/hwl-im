package com.hwl.im.server.receive;

import com.hwl.im.server.action.OnlineChannelManager;
import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.im.server.redis.store.TokenStore;
import com.hwl.imcore.improto.ImUserValidateRequest;
import com.hwl.imcore.improto.ImUserValidateResponse;
import com.hwl.imcore.improto.ImMessageResponse.Builder;

import java.util.UUID;
import java.util.function.Consumer;

public class UserValidateReceiveExecutor extends AbstractMessageReceiveExecutor<ImUserValidateRequest> {

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
    public boolean isCheckSession() {
        return false;
    }

    @Override
    public void executeCore(Builder response) {
        if (!checkUserInfo()) {
            response.setUserValidateResponse(ImUserValidateResponse.newBuilder().setIsSuccess(false)
                    .setMessage("User id or token is invalid").build());
            ServerMessageOperator.getInstance().push(channel, getMessageContext(response));
            return;
        }

        //// get user is online or not by userid
        // String sessionid =
        //// OnlineChannelManager.getInstance().getSession(request.getUserId());
        // if (sessionid != null && !sessionid.isEmpty()) {
        // //if user is online
        // //send message to online user
        // }

        final String newSessionid = UUID.randomUUID().toString().replace("-", "");
        OnlineChannelManager.getInstance().setChannelSessionid(request.getUserId(), newSessionid, channel,
                new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean succ) {
                        if (succ) {
                            response.setUserValidateResponse(ImUserValidateResponse.newBuilder().setIsSuccess(true)
                                    .setIsOnline(false).setSession(newSessionid).build());
                            ServerMessageOperator.getInstance().startPush(request.getUserId());
                        } else {
                            response.setUserValidateResponse(ImUserValidateResponse.newBuilder().setIsSuccess(false)
                                    .setMessage("set session failed").setIsOnline(false).setSession(newSessionid)
                                    .build());
                        }
                        // push result to client
                        ServerMessageOperator.getInstance().push(channel, getMessageContext(response));
                    }
                });
    }

    private boolean checkUserInfo() {
        boolean flag = request.getToken().equals(TokenStore.getUserToken(request.getUserId()));
        return flag;
    }
}