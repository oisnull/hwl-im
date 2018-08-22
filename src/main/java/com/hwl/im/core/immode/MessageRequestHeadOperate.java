package com.hwl.im.core.immode;

import com.hwl.im.core.ImCoreCommon;
import com.hwl.im.core.proto.ImMessageRequestHead;

public class MessageRequestHeadOperate {

    private static String sessionid = "";
    private static String language = "ch-cn";
    private static String version = "1.0.0";

    public static void setSessionid(String sessid) {
        sessionid = sessid;
    }

    public static void setLanguage(String lang) {
        language = lang;
    }

    public static void setVersion(String ver) {
        version = ver;
    }

    public static boolean isSessionValid(ImMessageRequestHead requestHead) {
        return requestHead != null && requestHead.getSessionid() != null
                && !requestHead.getSessionid().trim().equals("");
    }

    public static ImMessageRequestHead buildRequestHead() {
        ImMessageRequestHead requestHead = ImMessageRequestHead.newBuilder()
                .setClient(ImCoreCommon.getLocalHostLANAddress()).setLanguage(language).setSessionid(sessionid)
                .setTimestamp(System.currentTimeMillis()).setVersion(version).build();
        return requestHead;
    }
}