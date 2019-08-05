package com.hwl.im.server.default;

import com.hwl.imcore.improto.ImMessageRequestHead;

public class DefaultRequestValidator implements IRequestValidator
{
    public boolean isSessionValid(ImMessageRequestHead requestHead)
    {
        return true;
    }
}