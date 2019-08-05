package com.hwl.im.server.redis;

public class CommonRequestValidator implements IRequestValidator
{
    public bool isSessionValid(ImMessageRequestHead requestHead)
    {
        return true;
    }
}