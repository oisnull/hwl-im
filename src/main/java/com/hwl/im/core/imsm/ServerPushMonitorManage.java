package com.hwl.im.core.imsm;

import java.util.Date;
import java.util.HashMap;

public class ServerPushMonitorManage {

    private final static HashMap<Long, MonitorModel> statusContainer = new HashMap<>();
    private static ServerPushMonitorManage instance = new ServerPushMonitorManage();

    public static ServerPushMonitorManage getInstance() {
        //if (instance == null)
        //    instance = new ServerPushMonitorManage();

        return instance;
    }

    public MonitorModel getModel(long userId) {
        if (userId <= 0) return new MonitorModel();

        MonitorModel model = statusContainer.get(userId);
        if (model == null) {
            model = new MonitorModel();
        }

        return model;
    }

    public void start(long userId) {
        if (userId <= 0) return;

        MonitorModel model = statusContainer.get(userId);
        if (model == null) {
            model = new MonitorModel();
        }

        synchronized (model) {
            model.setStatus(MonitorModel.STAUTS_RUNNING);
            model.setStartExecTime(new Date());
            model.setEndExecTime(null);
            statusContainer.put(userId, model);
        }
    }

    public void endError(long userId) {
        this.end(userId, MonitorModel.STAUTS_ERROR);
    }

    public void end(long userId, int status) {
        if (userId <= 0) return;

        MonitorModel model = statusContainer.get(userId);
        if (model == null) return;

        synchronized (model) {
            model.setStatus(status);
            model.setEndExecTime(new Date());
            statusContainer.put(userId, model);
        }
    }

    public void end(long userId) {
        this.end(userId, MonitorModel.STAUTS_COMPLETE);
    }

    public void removeStatus(long userId) {
        if (userId <= 0) return;
        statusContainer.remove(userId);
    }

    public int getStatus(long userId) {
        if (userId <= 0) return MonitorModel.STAUTS_NOTRUN;

        MonitorModel model = statusContainer.get(userId);
        if (model == null) return MonitorModel.STAUTS_NOTRUN;

        return model.getStatus();
    }

    public boolean isRunning(long userId) {
        return getStatus(userId) == MonitorModel.STAUTS_RUNNING;
    }

    public void addCount(long userId) {
        if (userId <= 0) return;

        MonitorModel model = statusContainer.get(userId);
        if (model == null) return;

        synchronized (model) {
            model.setMessageCount(model.getMessageCount() + 1);
            statusContainer.put(userId, model);
        }
    }
}