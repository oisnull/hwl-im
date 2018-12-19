package com.hwl.im.core.imstore;

import java.util.LinkedList;
import java.util.List;

import com.hwl.imcore.improto.ImMessageContext;

public interface OfflineMessageStorageMedia {

    void addMessage(long userid, ImMessageContext messageContext);
	
	void addMessages(long userid,LinkedList<ImMessageContext> messageContexts);

    List<ImMessageContext> getMessages(long userid);

    ImMessageContext pollMessage(long userid);
}