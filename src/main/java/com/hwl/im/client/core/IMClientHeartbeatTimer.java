package com.hwl.im.client.core;

import com.hwl.im.core.ImCoreConfig;

import java.util.Timer;
import java.util.TimerTask;

public class IMClientHeartbeatTimer {
    private static final Timer timer = new Timer();
    private static IMClientHeartbeatTimer instance = null;

    private IMClientHeartbeatTimer() {
    }

    public static IMClientHeartbeatTimer getInstance() {
        if (instance == null)
            instance = new IMClientHeartbeatTimer();

        return instance;
    }

    public void run(TimerTask task) {
        long internal = (ImCoreConfig.IDLE_TIMEOUT_SECONDS - 2) * 1000;
        timer.schedule(task, internal, internal);
    }

    public void stop() {
        timer.cancel();
    }
}
