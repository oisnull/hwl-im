package com.hwl.im.server.redis;

import com.hwl.im.server.extra.IRequestValidator;
import com.hwl.imcore.improto.ImMessageRequestHead;

public class CommonRequestValidator implements IRequestValidator {
    public boolean isSessionValid(ImMessageRequestHead requestHead) {
        return true;
    }
}