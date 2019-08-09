package com.hwl.im.client.core;

import com.hwl.imcore.improto.ImMessageRequestHead;

public class MessageRequestHeadManager {
    private static ImMessageRequestHead requestHead = null;

    private static void init() {
        if (requestHead == null) {
            requestHead = ImMessageRequestHead.newBuilder()
                    .setClient("")
                    .setSessionid("")
                    .setLanguage("ch-cn")
                    .setTimestamp(0)
                    .setVersion("1.0.0")
                    .build();
        }
    }

    public static ImMessageRequestHead getRequestHead() {
        init();
        return requestHead;
    }

    public static void setSessionId(String sessionId) {
        requestHead = ImMessageRequestHead.newBuilder()
                .setClient("")
                .setSessionid(sessionId)
                .setLanguage("ch-cn")
                .setTimestamp(0)
                .setVersion("1.0.0")
                .build();
    }
}