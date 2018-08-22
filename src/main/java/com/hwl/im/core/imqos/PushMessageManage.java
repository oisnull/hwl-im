package com.hwl.im.core.imqos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hwl.im.core.ThreadPoolUtil;

public class PushMessageManage {

    final static ExecutorService executorService = Executors.newFixedThreadPool(ThreadPoolUtil.ioIntesivePoolSize());

    public void offlineStart() {
    }

}