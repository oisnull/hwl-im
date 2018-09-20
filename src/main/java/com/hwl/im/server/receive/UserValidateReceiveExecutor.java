package com.hwl.im.server.receive;

import com.hwl.im.core.imaction.AbstractMessageReceivExecutor;
import com.hwl.im.core.immode.MessageOperate;
import com.hwl.im.core.imom.OnlineManage;
import com.hwl.im.improto.ImMessageType;
import com.hwl.im.improto.ImUserValidateRequest;
import com.hwl.im.improto.ImUserValidateResponse;
import com.hwl.im.improto.ImMessageResponse.Builder;

public class UserValidateReceiveExecutor extends AbstractMessageReceivExecutor<ImUserValidateRequest> {

    public UserValidateReceiveExecutor(ImUserValidateRequest request) {
        super(request);
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getUserId() <= 0)
            throw new NullPointerException("userid con not be empty");
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

        if (!checkUserInfo()) {
            response.setUserValidateResponse(
                    ImUserValidateResponse.newBuilder().setIsSuccess(false).setMessage("Userid or token is invalid").build());
            return;
        }

        // get user is online or not by userid
        String sessionid = OnlineManage.getInstance().getSession(request.getUserId());
        if (sessionid == null || sessionid.isEmpty()) {
            // if not and generate sessionid
            sessionid = request.getUserId() + "-sessionid-test-001";
            OnlineManage.getInstance().setChannelSessionid(request.getUserId(), sessionid, channel);
            response.setUserValidateResponse(
                    ImUserValidateResponse.newBuilder().setIsSuccess(true).setIsOnline(false).setSessionid(sessionid).build());

            // start offline message push process
            MessageOperate.serverPush(request.getUserId());
        } else {
            response.setUserValidateResponse(
                    ImUserValidateResponse.newBuilder().setIsSuccess(false).setIsOnline(true).setMessage("User is online").setSessionid(sessionid).build());
        }
    }

    private boolean checkUserInfo() {
        return request.getToken().equals("123456");
    }

}