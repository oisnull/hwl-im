package com.hwl.im.server;

import java.util.function.Function;

import com.hwl.im.core.imaction.MessageReceiveExecutor;
import com.hwl.im.core.imom.OnlineSessionStorageMedia;
import com.hwl.im.core.imstore.OfflineMessageStorageMedia;
import com.hwl.imcore.improto.ImMessageRequest;
import com.hwl.imcore.improto.ImMessageType;

public class IMServerLauncherConfig {

    public OnlineSessionStorageMedia sessionStorageMedia = null;
    public OfflineMessageStorageMedia messageStorageMedia= null;

    public void registerReceiveExecutor(ImMessageType messageType,
            Function<ImMessageRequest, MessageReceiveExecutor> executorFunction) {
        MessageExecuteFactory.registerReceiveExecutor(messageType, executorFunction);
    }

   
}