package com.hwl.im.server.action;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ServerPushMonitor {
    private ConcurrentHashMap<Long, PushModel> statusContainer = null;

    public ServerPushMonitor() {
        statusContainer = new ConcurrentHashMap<Long, PushModel>();
    }

    public PushModel getModel(long userId) {
        if (userId <= 0 || !statusContainer.containsKey(userId)) return new PushModel();

        return statusContainer.get(userId);
    }

    public void start(long userId) {
        if (userId <= 0) return;

        PushModel model;
        if (!statusContainer.containsKey(userId)) {
            model = new PushModel();
            statusContainer.put(userId, model);
        } else {
            model = statusContainer.get(userId);
        }

        model.setStatus(PushModel.STAUTS_RUNNING);
        model.setStartExecTime(new Date());
        model.setEndExecTime(null);
        model.setMessageCount(0);
    }

    public void endError(long userId) {
        this.end(userId, PushModel.STAUTS_ERROR);
    }

    public void end(long userId, int status) {
        if (userId <= 0 || !statusContainer.containsKey(userId)) return;

        PushModel model = statusContainer.get(userId);
        if (model == null) return;

        model.setStatus(status);
        model.setEndExecTime(new Date());
        statusContainer.put(userId, model);
    }

    public void end(long userId) {
        this.end(userId, PushModel.STAUTS_COMPLETE);
    }

    public void removeStatus(long userId) {
        if (userId <= 0 || !statusContainer.containsKey(userId)) return;
        statusContainer.remove(userId);
    }

    public int getStatus(long userId) {
        if (userId <= 0 || !statusContainer.containsKey(userId)) return PushModel.STAUTS_NOTRUN;

        PushModel model = statusContainer.get(userId);
        if (model == null) return PushModel.STAUTS_NOTRUN;

        return model.getStatus();
    }

    public boolean isRunning(long userId) {
        return getStatus(userId) == PushModel.STAUTS_RUNNING;
    }

    public void addCount(long userId) {
        if (userId <= 0 || !statusContainer.containsKey(userId)) return;

        PushModel model = statusContainer.get(userId);
        model.setMessageCount(model.getMessageCount() + 1);
    }
}