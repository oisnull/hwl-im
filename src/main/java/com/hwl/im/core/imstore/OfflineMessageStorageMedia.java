package com.hwl.im.core.imstore;

import java.util.List;

import com.hwl.im.core.proto.ImMessageContext;

public interface OfflineMessageStorageMedia {

    void addMessage(Long userid, ImMessageContext messageContext);

    List<ImMessageContext> getMessages(Long userid);

    ImMessageContext pollMessage(Long userid);
}