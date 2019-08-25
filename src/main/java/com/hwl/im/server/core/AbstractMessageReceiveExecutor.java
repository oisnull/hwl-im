package com.hwl.im.server.core;

import com.hwl.im.server.extra.IRequestValidator;
import com.hwl.imcore.improto.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.Channel;

import java.util.UUID;

public abstract class AbstractMessageReceiveExecutor<TRequest> implements ServerMessageReceiveExecutor {
    private static Logger log = LogManager.getLogger(AbstractMessageReceiveExecutor.class.getName());
    private IRequestValidator requestValidator;

    protected ImMessageRequestHead requestHead;
    protected ImMessageType messageType;
    protected TRequest request;
    protected Channel channel;

    public AbstractMessageReceiveExecutor(TRequest request) {
        this.request = request;
    }

    @Override
    public void setRequestValidator(IRequestValidator validator) {
        this.requestValidator = validator;
    }

    protected void checkRequestParams() {
        if (this.requestHead == null) {
            throw new NullPointerException("requestHead");
        }
        if (this.request == null) {
            throw new NullPointerException("request");
        }
        if (this.isCheckSession() && !requestValidator.isSessionValid(this.requestHead)) {
            throw new RequestSessionInvalidException("session id is invalid");
        }
    }

    @Override
    public ImMessageContext execute(ImMessageType messageType, ImMessageRequestHead requestHead, Channel channel) {
        this.requestHead = requestHead;
        this.messageType = messageType;
        this.channel = channel;

        ImMessageResponseHead.Builder responseHead = ImMessageResponseHead.newBuilder()
                .setCode(ImMessageResponseCode.Success_VALUE);
        ImMessageResponse.Builder msgResponse = ImMessageResponse.newBuilder().setResponseHead(responseHead);

        try {
            if (isAck()) {
                String messageId = UUID.randomUUID().toString().replace("-", "");
                responseHead.setIsack(true).setMessageid(messageId).build();
				msgResponse.setResponseHead(responseHead);
            }
            this.checkRequestParams();
            this.executeCore(msgResponse);
            return null;
        } catch (RequestSessionInvalidException e) {
            log.error("Server session invalid exception executor : {}", e.getMessage());
            responseHead.setCode(ImMessageResponseCode.SessionInvalid_VALUE)
						.setMessage(e.getMessage())
						.setIsack(false)
						.build();
			msgResponse.setResponseHead(responseHead);
        } catch (Exception e) {
            log.error("Server exception executor : {}", e.getMessage());
            responseHead.setCode(ImMessageResponseCode.Failed_VALUE)
						.setMessage(e.getMessage())
						.setIsack(false)
						.build();
			msgResponse.setResponseHead(responseHead);
        }

        return getMessageContext(msgResponse);
    }

    public ImMessageContext getMessageContext(ImMessageResponse.Builder response) {
        return ImMessageContext.newBuilder().setResponse(response).setType(messageType).build();
    }

    public boolean isAck() {
        return false;
    }

    public boolean isCheckSession() {
        return true;
    }

    public abstract void executeCore(final ImMessageResponse.Builder response);
}