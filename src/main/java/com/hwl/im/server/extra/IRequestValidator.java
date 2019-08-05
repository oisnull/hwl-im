package com.hwl.im.server.extra;

import com.hwl.imcore.improto.ImMessageRequestHead;

public interface IRequestValidator {
    boolean isSessionValid(ImMessageRequestHead requestHead);
}