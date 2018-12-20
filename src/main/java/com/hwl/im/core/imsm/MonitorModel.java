package com.hwl.im.core.imsm;

import java.util.Date;

public class MonitorModel {
	
	public final static int STAUTS_NOTRUN=0;
	public final static int STAUTS_RUNNING=1;
	public final static int STAUTS_COMPLETE=2;
	public final static int STAUTS_ERROR=3;

	private int status;
	private Date startExecTime;
	private Date endExecTime;

    /**
     * @return int return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return Date return the startExecTime
     */
    public Date getStartExecTime() {
        return startExecTime;
    }

    /**
     * @param startExecTime the startExecTime to set
     */
    public void setStartExecTime(Date startExecTime) {
        this.startExecTime = startExecTime;
    }

    /**
     * @return Date return the endExecTime
     */
    public Date getEndExecTime() {
        return endExecTime;
    }

    /**
     * @param endExecTime the endExecTime to set
     */
    public void setEndExecTime(Date endExecTime) {
        this.endExecTime = endExecTime;
    }

}
