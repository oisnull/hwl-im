package com.hwl.im.server.defa;

import com.hwl.im.server.extra.IRequestValidator;
import com.hwl.imcore.improto.ImMessageRequestHead;

public class DefaultRequestValidator implements IRequestValidator {
    public boolean isSessionValid(ImMessageRequestHead requestHead) {
        return true;
    }
}