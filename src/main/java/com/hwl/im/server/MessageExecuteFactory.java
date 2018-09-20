package com.hwl.im.server;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.hwl.im.core.imaction.MessageReceiveExecutor;
import com.hwl.im.improto.ImMessageContext;
import com.hwl.im.improto.ImMessageRequest;
import com.hwl.im.improto.ImMessageType;

public class MessageExecuteFactory {
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

    public static MessageReceiveExecutor create(ImMessageContext requestMessageContext) {
        if (requestMessageContext == null || receiveExecutors.size() <= 0)
            return null;

        ImMessageRequest request = requestMessageContext.getRequest();
        if (request == null)
            return null;

        Function<ImMessageRequest, MessageReceiveExecutor> executorFunction = receiveExecutors
                .get(requestMessageContext.getType());
        if (executorFunction == null)
            return null;

        MessageReceiveExecutor executor = executorFunction.apply(request);
        if (executor == null)
            return null;

        executor.setRequestHead(request.getRequestHead());
        return executor;
    }
}
