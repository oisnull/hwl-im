package com.hwl.im.client.core;

public class MessageRequestHeadManager {
    private final static ImMessageRequestHead requestHead = new ImMessageRequestHead();

	static{
        requestHead.Client = "",
        requestHead.Language = "ch-cn",
        requestHead.Sessionid = "",
        requestHead.Timestamp = 0,
        requestHead.Version = "1.0.0",
	}

    public static ImMessageRequestHead getRequestHead()
    {
        return requestHead;
    }

    public static void setSessionId(string sessionId)
    {
        requestHead.Sessionid = sessionId;
    }
}