package com.hwl.im.server.extra;

import java.util.function.Consumer;

public interface IOfflineMessageStorage {
    void addFirst(long userid, ImMessageContext messageContext);

    void addMessage(long userid, ImMessageContext messageContext);

    void addMessages(long userid, List<ImMessageContext> messageContexts);

    List<ImMessageContext> getMessages(long userid);

    ImMessageContext pollMessage(long userid);
}