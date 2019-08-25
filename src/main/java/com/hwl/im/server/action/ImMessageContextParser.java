package com.hwl.im.server.action;

import com.hwl.imcore.improto.ImMessageContext;

public class ImMessageContextParser {

    public static boolean isAck(ImMessageContext messageContext) {
        if (messageContext != null && messageContext.getResponse() != null
                && messageContext.getResponse().getResponseHead() != null) {
            return messageContext.getResponse().getResponseHead().getIsack();
        }
        return false;
    }

    public static String getMessageId(ImMessageContext messageContext) {
        if (messageContext != null && messageContext.getResponse() != null
                && messageContext.getResponse().getResponseHead() != null) {
            return messageContext.getResponse().getResponseHead().getMessageid();
        }
        return null;
    }
	
}