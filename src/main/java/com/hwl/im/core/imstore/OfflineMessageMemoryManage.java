package com.hwl.im.core.imstore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hwl.imcore.improto.ImMessageContext;

public class OfflineMessageMemoryManage implements OfflineMessageStorageMedia {

	private static Map<Long, LinkedList<ImMessageContext>> offlineMessageContainer = new HashMap<>();

	@Override
	public void addMessage(long userid, ImMessageContext messageContext) {
		if (userid <= 0 || messageContext == null)
			return;
		if (offlineMessageContainer.containsKey(userid)) {
			offlineMessageContainer.get(userid).add(messageContext);
		} else {
			LinkedList<ImMessageContext> messages = new LinkedList<>();
			messages.add(messageContext);
			offlineMessageContainer.put(userid, messages);
		}
	}

	@Override
	public void addMessages(long userid, LinkedList<ImMessageContext> messageContexts) {
		if (userid <= 0 || messageContexts == null||messageContexts.size()<=0)
			return;
		if (offlineMessageContainer.containsKey(userid)) {
			offlineMessageContainer.get(userid).addAll(messageContexts);
		} else {
			offlineMessageContainer.put(userid, messageContexts);
		}
	}	

	@Override
	public List<ImMessageContext> getMessages(long userid) {
		if (userid <= 0)
			return null;

		return offlineMessageContainer.get(userid);
	}

	@Override
	public ImMessageContext pollMessage(long userid) {
		if (userid <= 0)
			return null;

		LinkedList<ImMessageContext> messageContexts = offlineMessageContainer.get(userid);
		if (messageContexts == null || messageContexts.size() <= 0)
			return null;

		return messageContexts.poll();
	}

}