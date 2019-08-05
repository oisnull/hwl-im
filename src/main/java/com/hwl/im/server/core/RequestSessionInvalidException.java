package com.hwl.im.server.core;

public class RequestSessionInvalidException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1234567894567L;

	public RequestSessionInvalidException(String msg) {
            super(msg);
        }
}