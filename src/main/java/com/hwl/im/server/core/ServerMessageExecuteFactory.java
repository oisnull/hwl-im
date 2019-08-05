package com.hwl.im.server.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.hwl.im.core.imaction.MessageReceiveExecutor;
import com.hwl.imcore.improto.ImMessageContext;
import com.hwl.imcore.improto.ImMessageRequest;
import com.hwl.imcore.improto.ImMessageType;

public class ServerMessageExecuteFactory {
    private static Map<ImMessageType, Function<ImMessageRequest, MessageReceiveExecutor>> receiveExecutors = new HashMap<>();

    public static void registerReceiveExecutor(ImMessageType messageType,
            Function<ImMessageRequest, MessageReceiveExecutor> executorFunction) {
        if (receiveExecutors.containsKey(messageType)) {
            receiveExecutors.remove(messageType);
        }
        receiveExecutors.put(messageType, executorFunction);
    }

    public static void unregisterReceiveExecutor(ImMessageType messageType) {
        if (receiveExecutors.containsKey(messageType)) {
            receiveExecutors.remove(messageType);
        }
    }

    public static MessageReceiveExecutor create(ImMessageType messageType,ImMessageRequest request) {
        if (request == null || receiveExecutors.size() <= 0)
            return null;

        Function<ImMessageRequest, MessageReceiveExecutor> executorFunction = receiveExecutors.get(messageType);
        if (executorFunction == null)
            return null;

        return executorFunction.apply(request);
    }
}
