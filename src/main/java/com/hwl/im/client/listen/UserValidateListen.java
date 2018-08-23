package com.hwl.im.client.listen;

import java.util.function.Consumer;

import com.hwl.im.core.imaction.AbstractMessageListenExecutor;
import com.hwl.im.core.proto.ImMessageResponse;
import com.hwl.im.core.proto.ImUserValidateResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserValidateListen extends AbstractMessageListenExecutor<ImUserValidateResponse> {

    private Consumer<String> succCallback;
    private Consumer<String> failedCallback;
    static Logger log = LogManager.getLogger(UserValidateListen.class.getName());

    public UserValidateListen(Consumer<String> succCallback) {
        this.succCallback = succCallback;
    }

    public UserValidateListen(Consumer<String> succCallback, Consumer<String> failedCallback) {
        this.succCallback = succCallback;
        this.failedCallback = failedCallback;
    }

    @Override
    public void success(ImUserValidateResponse response) {
        super.success(response);

        log.debug("User validate {} , listen content : {}", response.getIsSuccess() ? "success" : "failed",
                response.toString());

        if (response.getIsSuccess()) {
            if (this.succCallback != null)
                this.succCallback.accept(response.getSessionid());
        } else {
            if (this.failedCallback != null)
                this.failedCallback.accept(response.getMessage());
        }
    }

    @Override
    public void failed(int responseCode, String message) {
        log.debug("User validate receive failed : {}", message);
        if (this.failedCallback != null)
            this.failedCallback.accept(message);
    }

    @Override
    public ImUserValidateResponse getResponse(ImMessageResponse response) {
        return response.getUserValidateResponse();
    }

}