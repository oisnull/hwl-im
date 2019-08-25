package com.hwl.im.server.action;

import java.util.Date;

public class PushModel {
    public final static int STAUTS_NOTRUN = 0;
    public final static int STAUTS_RUNNING = 1;
    public final static int STAUTS_COMPLETE = 2;
    public final static int STAUTS_ERROR = 3;

    private int status;
    private Date startExecTime;
    private Date endExecTime;
    private int messageCount;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartExecTime() {
        return startExecTime;
    }

    public void setStartExecTime(Date startExecTime) {
        this.startExecTime = startExecTime;
    }

    public Date getEndExecTime() {
        return endExecTime;
    }

    public void setEndExecTime(Date endExecTime) {
        this.endExecTime = endExecTime;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }
}