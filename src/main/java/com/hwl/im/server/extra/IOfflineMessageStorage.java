package com.hwl.im.server.extra;

import com.hwl.imcore.improto.ImMessageContext;

import java.util.List;

public interface IOfflineMessageStorage {
    void addFirst(long userid, ImMessageContext messageContext);

    void addMessage(long userid, ImMessageContext messageContext);

    void addMessages(long userid, List<ImMessageContext> messageContexts);

    List<ImMessageContext> getMessages(long userid);

    ImMessageContext pollMessage(long userid);
}