package com.hwl.im.core;

public class ImCoreConfig {

    /**
     * 空闲超时时间（秒）
     */
    public static final int IDLE_TIMEOUT_SECONDS = 3 * 60;

    /**
     * 消息发送失败后重试的次数
     */
    public static final int MESSAGE_SEND_FAILED_RETRY_COUNT = 3;
}
