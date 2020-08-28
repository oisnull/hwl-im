package com.hwl.im.server.receive;

import com.hwl.im.server.action.OnlineChannelManager;
import com.hwl.im.server.action.ServerMessageOperator;
import com.hwl.im.server.core.AbstractMessageReceiveExecutor;
import com.hwl.imcore.improto.*;

public class AppVersionMessageReceiveExecutor extends AbstractMessageReceiveExecutor<ImAppVersionRequest> {

    ImAppVersionContent appVersionContent = null;

    public AppVersionMessageReceiveExecutor(ImAppVersionRequest appVersionRequest) {
        super(appVersionRequest);
    }

    @Override
    protected void checkRequestParams() {
        super.checkRequestParams();
        if (request.getAppVersionContent() == null) {
            throw new NullPointerException("ImAppVersionContent");
        } else {
            appVersionContent = request.getAppVersionContent();
        }
    }

    @Override
    public void executeCore(ImMessageResponse.Builder response) {
        response.setAppVersionResponse(ImAppVersionResponse.newBuilder()
                .setAppVersionContent(appVersionContent)
                .setBuildTime(System.currentTimeMillis()).build());
        ImMessageContext messageContext = super.getMessageContext(response);

        if(request.getUserIdsList().size() > 0){
            for (Long user : request.getUserIdsList()) {
                if (!OnlineChannelManager.getInstance().isOnline(user)) {
                    continue;
                }
                ServerMessageOperator.getInstance().push(user, messageContext, false);
            }
        }
    }
}
