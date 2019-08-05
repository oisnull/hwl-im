package com.hwl.im.server.action;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class ServerPushMonitor {
	private ConcurrentHashMap<long, PushModel> statusContainer = null;

    public ServerPushMonitor()
    {
        statusContainer = new ConcurrentHashMap<long, PushModel>();
    }

    public PushModel getModel(long userId)
    {
        if (userId <= 0 || !statusContainer.ContainsKey(userId)) return new PushModel();

        return statusContainer[userId];
    }

    public void start(long userId)
    {
        if (userId <= 0) return;

        PushModel model;
        if (!statusContainer.ContainsKey(userId))
        {
            model = new PushModel();
            statusContainer.Add(userId, model);
        }
        else
        {
            model = statusContainer[userId];
        }

        model.setStatus(PushModel.STAUTS_RUNNING);
        model.setStartExecTime(DateTime.Now);
        model.setEndExecTime(null);
        model.setMessageCount(0);
    }

    public void endError(long userId)
    {
        this.end(userId, PushModel.STAUTS_ERROR);
    }

    public void end(long userId, int status)
    {
        if (userId <= 0 || !statusContainer.ContainsKey(userId)) return;

        PushModel model = statusContainer[userId];
        if (model == null) return;

        model.setStatus(status);
        model.setEndExecTime(DateTime.Now);
        statusContainer.Add(userId, model);
    }

    public void end(long userId)
    {
        this.end(userId, PushModel.STAUTS_COMPLETE);
    }

    public void removeStatus(long userId)
    {
        if (userId <= 0 || !statusContainer.ContainsKey(userId)) return;
        statusContainer.Remove(userId);
    }

    public int getStatus(long userId)
    {
        if (userId <= 0 || !statusContainer.ContainsKey(userId)) return PushModel.STAUTS_NOTRUN;

        PushModel model = statusContainer[userId];
        if (model == null) return PushModel.STAUTS_NOTRUN;

        return model.getStatus();
    }

    public bool isRunning(long userId)
    {
        return getStatus(userId) == PushModel.STAUTS_RUNNING;
    }

    public void addCount(long userId)
    {
        if (userId <= 0 || !statusContainer.ContainsKey(userId)) return;

        PushModel model = statusContainer[userId];
        model.setMessageCount(model.getMessageCount() + 1);
    }
}