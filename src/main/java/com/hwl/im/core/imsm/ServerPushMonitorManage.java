package com.hwl.im.core.imsm;

import java.util.LinkedList;

import com.hwl.imcore.improto.ImMessageContext;

public class ServerPushMonitorManage {

    private final static HashMap<Long, MonitorModel> statusContainer = new ConcurrentHashMap<>();
    private static ServerPushMonitorManage instance = new ServerPushMonitorManage();

    public static ServerPushMonitorManage getInstance() {
        //if (instance == null)
        //    instance = new ServerPushMonitorManage();

        return instance;
    }

	public void start(long userId){
		if(userId<=0) return;

		MonitorModel model=statusContainer.get(userId);
		if(model==null){
			model=new MonitorModel();
		}

		synchronized(model){
			model.setStatus(MonitorModel.STAUTS_RUNNING);
			model.setStartExecTime(new Date());
			model.setEndExecTime(null);
			statusContainer.put(userId,model);
		}
	}

	public void end(long userId){
		if(userId<=0) return;

		MonitorModel model=statusContainer.get(userId);
		if(model==null) return;

		synchronized(model){
			model.setStatus(MonitorModel.STAUTS_COMPLETE);
			model.setEndExecTime(new Date());
			statusContainer.put(userId,model);
		}
	}

	public void removeStatus(long userId){
		if(userId<=0) return;
		statusContainer.remove(userId);
	}

	public int getStatus(long userId){
		if(userId<=0) return MonitorModel.STAUTS_NOTRUN;

		MonitorModel model=statusContainer.get(userId);
		if(model==null) return MonitorModel.STAUTS_NOTRUN;

		return model.getStatus();
	}
}