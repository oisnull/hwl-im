package com.hwl.im.core.imstore;

import java.util.List;

import com.hwl.im.improto.ImMessageContext;

public interface OfflineMessageStorageMedia {

    void addMessage(Long userid, ImMessageContext messageContext);

    List<ImMessageContext> getMessages(Long userid);

    ImMessageContext pollMessage(Long userid);
}