// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/java/com/hwl/im/protocol/im_message.proto

package com.hwl.im.core.proto;

public interface ImMessageRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ImMessageRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.ImMessageRequestHead requestHead = 1;</code>
   */
  boolean hasRequestHead();
  /**
   * <code>.ImMessageRequestHead requestHead = 1;</code>
   */
  com.hwl.im.core.proto.ImMessageRequestHead getRequestHead();
  /**
   * <code>.ImMessageRequestHead requestHead = 1;</code>
   */
  com.hwl.im.core.proto.ImMessageRequestHeadOrBuilder getRequestHeadOrBuilder();

  /**
   * <code>.ImUserValidateRequest userValidateRequest = 2;</code>
   */
  boolean hasUserValidateRequest();
  /**
   * <code>.ImUserValidateRequest userValidateRequest = 2;</code>
   */
  com.hwl.im.core.proto.ImUserValidateRequest getUserValidateRequest();
  /**
   * <code>.ImUserValidateRequest userValidateRequest = 2;</code>
   */
  com.hwl.im.core.proto.ImUserValidateRequestOrBuilder getUserValidateRequestOrBuilder();

  /**
   * <code>.ImChatUserMessageRequest chatUserMessageRequest = 3;</code>
   */
  boolean hasChatUserMessageRequest();
  /**
   * <code>.ImChatUserMessageRequest chatUserMessageRequest = 3;</code>
   */
  com.hwl.im.core.proto.ImChatUserMessageRequest getChatUserMessageRequest();
  /**
   * <code>.ImChatUserMessageRequest chatUserMessageRequest = 3;</code>
   */
  com.hwl.im.core.proto.ImChatUserMessageRequestOrBuilder getChatUserMessageRequestOrBuilder();

  /**
   * <code>.ImChatGroupMessageRequest chatGroupMessageRequest = 4;</code>
   */
  boolean hasChatGroupMessageRequest();
  /**
   * <code>.ImChatGroupMessageRequest chatGroupMessageRequest = 4;</code>
   */
  com.hwl.im.core.proto.ImChatGroupMessageRequest getChatGroupMessageRequest();
  /**
   * <code>.ImChatGroupMessageRequest chatGroupMessageRequest = 4;</code>
   */
  com.hwl.im.core.proto.ImChatGroupMessageRequestOrBuilder getChatGroupMessageRequestOrBuilder();

  /**
   * <code>.ImHeartBeatMessageRequest heartBeatMessageRequest = 5;</code>
   */
  boolean hasHeartBeatMessageRequest();
  /**
   * <code>.ImHeartBeatMessageRequest heartBeatMessageRequest = 5;</code>
   */
  com.hwl.im.core.proto.ImHeartBeatMessageRequest getHeartBeatMessageRequest();
  /**
   * <code>.ImHeartBeatMessageRequest heartBeatMessageRequest = 5;</code>
   */
  com.hwl.im.core.proto.ImHeartBeatMessageRequestOrBuilder getHeartBeatMessageRequestOrBuilder();

  public com.hwl.im.core.proto.ImMessageRequest.RequestBodyCase getRequestBodyCase();
}