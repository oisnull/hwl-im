// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/java/com/hwl/im/protocol/im_message.proto

package com.hwl.im.core.proto;

/**
 * Protobuf type {@code ImMessageContext}
 */
public  final class ImMessageContext extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ImMessageContext)
    ImMessageContextOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ImMessageContext.newBuilder() to construct.
  private ImMessageContext(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ImMessageContext() {
    type_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ImMessageContext(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 8: {
            int rawValue = input.readEnum();

            type_ = rawValue;
            break;
          }
          case 18: {
            com.hwl.im.core.proto.ImMessageRequest.Builder subBuilder = null;
            if (bodyCase_ == 2) {
              subBuilder = ((com.hwl.im.core.proto.ImMessageRequest) body_).toBuilder();
            }
            body_ =
                input.readMessage(com.hwl.im.core.proto.ImMessageRequest.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom((com.hwl.im.core.proto.ImMessageRequest) body_);
              body_ = subBuilder.buildPartial();
            }
            bodyCase_ = 2;
            break;
          }
          case 26: {
            com.hwl.im.core.proto.ImMessageResponse.Builder subBuilder = null;
            if (bodyCase_ == 3) {
              subBuilder = ((com.hwl.im.core.proto.ImMessageResponse) body_).toBuilder();
            }
            body_ =
                input.readMessage(com.hwl.im.core.proto.ImMessageResponse.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom((com.hwl.im.core.proto.ImMessageResponse) body_);
              body_ = subBuilder.buildPartial();
            }
            bodyCase_ = 3;
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.hwl.im.core.proto.ImMessage.internal_static_ImMessageContext_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.hwl.im.core.proto.ImMessage.internal_static_ImMessageContext_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.hwl.im.core.proto.ImMessageContext.class, com.hwl.im.core.proto.ImMessageContext.Builder.class);
  }

  private int bodyCase_ = 0;
  private java.lang.Object body_;
  public enum BodyCase
      implements com.google.protobuf.Internal.EnumLite {
    REQUEST(2),
    RESPONSE(3),
    BODY_NOT_SET(0);
    private final int value;
    private BodyCase(int value) {
      this.value = value;
    }
    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static BodyCase valueOf(int value) {
      return forNumber(value);
    }

    public static BodyCase forNumber(int value) {
      switch (value) {
        case 2: return REQUEST;
        case 3: return RESPONSE;
        case 0: return BODY_NOT_SET;
        default: return null;
      }
    }
    public int getNumber() {
      return this.value;
    }
  };

  public BodyCase
  getBodyCase() {
    return BodyCase.forNumber(
        bodyCase_);
  }

  public static final int TYPE_FIELD_NUMBER = 1;
  private int type_;
  /**
   * <code>.ImMessageType type = 1;</code>
   */
  public int getTypeValue() {
    return type_;
  }
  /**
   * <code>.ImMessageType type = 1;</code>
   */
  public com.hwl.im.core.proto.ImMessageType getType() {
    com.hwl.im.core.proto.ImMessageType result = com.hwl.im.core.proto.ImMessageType.valueOf(type_);
    return result == null ? com.hwl.im.core.proto.ImMessageType.UNRECOGNIZED : result;
  }

  public static final int REQUEST_FIELD_NUMBER = 2;
  /**
   * <code>.ImMessageRequest request = 2;</code>
   */
  public boolean hasRequest() {
    return bodyCase_ == 2;
  }
  /**
   * <code>.ImMessageRequest request = 2;</code>
   */
  public com.hwl.im.core.proto.ImMessageRequest getRequest() {
    if (bodyCase_ == 2) {
       return (com.hwl.im.core.proto.ImMessageRequest) body_;
    }
    return com.hwl.im.core.proto.ImMessageRequest.getDefaultInstance();
  }
  /**
   * <code>.ImMessageRequest request = 2;</code>
   */
  public com.hwl.im.core.proto.ImMessageRequestOrBuilder getRequestOrBuilder() {
    if (bodyCase_ == 2) {
       return (com.hwl.im.core.proto.ImMessageRequest) body_;
    }
    return com.hwl.im.core.proto.ImMessageRequest.getDefaultInstance();
  }

  public static final int RESPONSE_FIELD_NUMBER = 3;
  /**
   * <code>.ImMessageResponse response = 3;</code>
   */
  public boolean hasResponse() {
    return bodyCase_ == 3;
  }
  /**
   * <code>.ImMessageResponse response = 3;</code>
   */
  public com.hwl.im.core.proto.ImMessageResponse getResponse() {
    if (bodyCase_ == 3) {
       return (com.hwl.im.core.proto.ImMessageResponse) body_;
    }
    return com.hwl.im.core.proto.ImMessageResponse.getDefaultInstance();
  }
  /**
   * <code>.ImMessageResponse response = 3;</code>
   */
  public com.hwl.im.core.proto.ImMessageResponseOrBuilder getResponseOrBuilder() {
    if (bodyCase_ == 3) {
       return (com.hwl.im.core.proto.ImMessageResponse) body_;
    }
    return com.hwl.im.core.proto.ImMessageResponse.getDefaultInstance();
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (type_ != com.hwl.im.core.proto.ImMessageType.Base.getNumber()) {
      output.writeEnum(1, type_);
    }
    if (bodyCase_ == 2) {
      output.writeMessage(2, (com.hwl.im.core.proto.ImMessageRequest) body_);
    }
    if (bodyCase_ == 3) {
      output.writeMessage(3, (com.hwl.im.core.proto.ImMessageResponse) body_);
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (type_ != com.hwl.im.core.proto.ImMessageType.Base.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, type_);
    }
    if (bodyCase_ == 2) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, (com.hwl.im.core.proto.ImMessageRequest) body_);
    }
    if (bodyCase_ == 3) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, (com.hwl.im.core.proto.ImMessageResponse) body_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.hwl.im.core.proto.ImMessageContext)) {
      return super.equals(obj);
    }
    com.hwl.im.core.proto.ImMessageContext other = (com.hwl.im.core.proto.ImMessageContext) obj;

    boolean result = true;
    result = result && type_ == other.type_;
    result = result && getBodyCase().equals(
        other.getBodyCase());
    if (!result) return false;
    switch (bodyCase_) {
      case 2:
        result = result && getRequest()
            .equals(other.getRequest());
        break;
      case 3:
        result = result && getResponse()
            .equals(other.getResponse());
        break;
      case 0:
      default:
    }
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + TYPE_FIELD_NUMBER;
    hash = (53 * hash) + type_;
    switch (bodyCase_) {
      case 2:
        hash = (37 * hash) + REQUEST_FIELD_NUMBER;
        hash = (53 * hash) + getRequest().hashCode();
        break;
      case 3:
        hash = (37 * hash) + RESPONSE_FIELD_NUMBER;
        hash = (53 * hash) + getResponse().hashCode();
        break;
      case 0:
      default:
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.hwl.im.core.proto.ImMessageContext parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.hwl.im.core.proto.ImMessageContext parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.hwl.im.core.proto.ImMessageContext prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code ImMessageContext}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ImMessageContext)
      com.hwl.im.core.proto.ImMessageContextOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.hwl.im.core.proto.ImMessage.internal_static_ImMessageContext_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.hwl.im.core.proto.ImMessage.internal_static_ImMessageContext_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.hwl.im.core.proto.ImMessageContext.class, com.hwl.im.core.proto.ImMessageContext.Builder.class);
    }

    // Construct using com.hwl.im.core.proto.ImMessageContext.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      type_ = 0;

      bodyCase_ = 0;
      body_ = null;
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.hwl.im.core.proto.ImMessage.internal_static_ImMessageContext_descriptor;
    }

    public com.hwl.im.core.proto.ImMessageContext getDefaultInstanceForType() {
      return com.hwl.im.core.proto.ImMessageContext.getDefaultInstance();
    }

    public com.hwl.im.core.proto.ImMessageContext build() {
      com.hwl.im.core.proto.ImMessageContext result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.hwl.im.core.proto.ImMessageContext buildPartial() {
      com.hwl.im.core.proto.ImMessageContext result = new com.hwl.im.core.proto.ImMessageContext(this);
      result.type_ = type_;
      if (bodyCase_ == 2) {
        if (requestBuilder_ == null) {
          result.body_ = body_;
        } else {
          result.body_ = requestBuilder_.build();
        }
      }
      if (bodyCase_ == 3) {
        if (responseBuilder_ == null) {
          result.body_ = body_;
        } else {
          result.body_ = responseBuilder_.build();
        }
      }
      result.bodyCase_ = bodyCase_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.hwl.im.core.proto.ImMessageContext) {
        return mergeFrom((com.hwl.im.core.proto.ImMessageContext)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.hwl.im.core.proto.ImMessageContext other) {
      if (other == com.hwl.im.core.proto.ImMessageContext.getDefaultInstance()) return this;
      if (other.type_ != 0) {
        setTypeValue(other.getTypeValue());
      }
      switch (other.getBodyCase()) {
        case REQUEST: {
          mergeRequest(other.getRequest());
          break;
        }
        case RESPONSE: {
          mergeResponse(other.getResponse());
          break;
        }
        case BODY_NOT_SET: {
          break;
        }
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.hwl.im.core.proto.ImMessageContext parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.hwl.im.core.proto.ImMessageContext) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bodyCase_ = 0;
    private java.lang.Object body_;
    public BodyCase
        getBodyCase() {
      return BodyCase.forNumber(
          bodyCase_);
    }

    public Builder clearBody() {
      bodyCase_ = 0;
      body_ = null;
      onChanged();
      return this;
    }


    private int type_ = 0;
    /**
     * <code>.ImMessageType type = 1;</code>
     */
    public int getTypeValue() {
      return type_;
    }
    /**
     * <code>.ImMessageType type = 1;</code>
     */
    public Builder setTypeValue(int value) {
      type_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.ImMessageType type = 1;</code>
     */
    public com.hwl.im.core.proto.ImMessageType getType() {
      com.hwl.im.core.proto.ImMessageType result = com.hwl.im.core.proto.ImMessageType.valueOf(type_);
      return result == null ? com.hwl.im.core.proto.ImMessageType.UNRECOGNIZED : result;
    }
    /**
     * <code>.ImMessageType type = 1;</code>
     */
    public Builder setType(com.hwl.im.core.proto.ImMessageType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      type_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.ImMessageType type = 1;</code>
     */
    public Builder clearType() {
      
      type_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.SingleFieldBuilderV3<
        com.hwl.im.core.proto.ImMessageRequest, com.hwl.im.core.proto.ImMessageRequest.Builder, com.hwl.im.core.proto.ImMessageRequestOrBuilder> requestBuilder_;
    /**
     * <code>.ImMessageRequest request = 2;</code>
     */
    public boolean hasRequest() {
      return bodyCase_ == 2;
    }
    /**
     * <code>.ImMessageRequest request = 2;</code>
     */
    public com.hwl.im.core.proto.ImMessageRequest getRequest() {
      if (requestBuilder_ == null) {
        if (bodyCase_ == 2) {
          return (com.hwl.im.core.proto.ImMessageRequest) body_;
        }
        return com.hwl.im.core.proto.ImMessageRequest.getDefaultInstance();
      } else {
        if (bodyCase_ == 2) {
          return requestBuilder_.getMessage();
        }
        return com.hwl.im.core.proto.ImMessageRequest.getDefaultInstance();
      }
    }
    /**
     * <code>.ImMessageRequest request = 2;</code>
     */
    public Builder setRequest(com.hwl.im.core.proto.ImMessageRequest value) {
      if (requestBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        body_ = value;
        onChanged();
      } else {
        requestBuilder_.setMessage(value);
      }
      bodyCase_ = 2;
      return this;
    }
    /**
     * <code>.ImMessageRequest request = 2;</code>
     */
    public Builder setRequest(
        com.hwl.im.core.proto.ImMessageRequest.Builder builderForValue) {
      if (requestBuilder_ == null) {
        body_ = builderForValue.build();
        onChanged();
      } else {
        requestBuilder_.setMessage(builderForValue.build());
      }
      bodyCase_ = 2;
      return this;
    }
    /**
     * <code>.ImMessageRequest request = 2;</code>
     */
    public Builder mergeRequest(com.hwl.im.core.proto.ImMessageRequest value) {
      if (requestBuilder_ == null) {
        if (bodyCase_ == 2 &&
            body_ != com.hwl.im.core.proto.ImMessageRequest.getDefaultInstance()) {
          body_ = com.hwl.im.core.proto.ImMessageRequest.newBuilder((com.hwl.im.core.proto.ImMessageRequest) body_)
              .mergeFrom(value).buildPartial();
        } else {
          body_ = value;
        }
        onChanged();
      } else {
        if (bodyCase_ == 2) {
          requestBuilder_.mergeFrom(value);
        }
        requestBuilder_.setMessage(value);
      }
      bodyCase_ = 2;
      return this;
    }
    /**
     * <code>.ImMessageRequest request = 2;</code>
     */
    public Builder clearRequest() {
      if (requestBuilder_ == null) {
        if (bodyCase_ == 2) {
          bodyCase_ = 0;
          body_ = null;
          onChanged();
        }
      } else {
        if (bodyCase_ == 2) {
          bodyCase_ = 0;
          body_ = null;
        }
        requestBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>.ImMessageRequest request = 2;</code>
     */
    public com.hwl.im.core.proto.ImMessageRequest.Builder getRequestBuilder() {
      return getRequestFieldBuilder().getBuilder();
    }
    /**
     * <code>.ImMessageRequest request = 2;</code>
     */
    public com.hwl.im.core.proto.ImMessageRequestOrBuilder getRequestOrBuilder() {
      if ((bodyCase_ == 2) && (requestBuilder_ != null)) {
        return requestBuilder_.getMessageOrBuilder();
      } else {
        if (bodyCase_ == 2) {
          return (com.hwl.im.core.proto.ImMessageRequest) body_;
        }
        return com.hwl.im.core.proto.ImMessageRequest.getDefaultInstance();
      }
    }
    /**
     * <code>.ImMessageRequest request = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.hwl.im.core.proto.ImMessageRequest, com.hwl.im.core.proto.ImMessageRequest.Builder, com.hwl.im.core.proto.ImMessageRequestOrBuilder> 
        getRequestFieldBuilder() {
      if (requestBuilder_ == null) {
        if (!(bodyCase_ == 2)) {
          body_ = com.hwl.im.core.proto.ImMessageRequest.getDefaultInstance();
        }
        requestBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.hwl.im.core.proto.ImMessageRequest, com.hwl.im.core.proto.ImMessageRequest.Builder, com.hwl.im.core.proto.ImMessageRequestOrBuilder>(
                (com.hwl.im.core.proto.ImMessageRequest) body_,
                getParentForChildren(),
                isClean());
        body_ = null;
      }
      bodyCase_ = 2;
      onChanged();;
      return requestBuilder_;
    }

    private com.google.protobuf.SingleFieldBuilderV3<
        com.hwl.im.core.proto.ImMessageResponse, com.hwl.im.core.proto.ImMessageResponse.Builder, com.hwl.im.core.proto.ImMessageResponseOrBuilder> responseBuilder_;
    /**
     * <code>.ImMessageResponse response = 3;</code>
     */
    public boolean hasResponse() {
      return bodyCase_ == 3;
    }
    /**
     * <code>.ImMessageResponse response = 3;</code>
     */
    public com.hwl.im.core.proto.ImMessageResponse getResponse() {
      if (responseBuilder_ == null) {
        if (bodyCase_ == 3) {
          return (com.hwl.im.core.proto.ImMessageResponse) body_;
        }
        return com.hwl.im.core.proto.ImMessageResponse.getDefaultInstance();
      } else {
        if (bodyCase_ == 3) {
          return responseBuilder_.getMessage();
        }
        return com.hwl.im.core.proto.ImMessageResponse.getDefaultInstance();
      }
    }
    /**
     * <code>.ImMessageResponse response = 3;</code>
     */
    public Builder setResponse(com.hwl.im.core.proto.ImMessageResponse value) {
      if (responseBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        body_ = value;
        onChanged();
      } else {
        responseBuilder_.setMessage(value);
      }
      bodyCase_ = 3;
      return this;
    }
    /**
     * <code>.ImMessageResponse response = 3;</code>
     */
    public Builder setResponse(
        com.hwl.im.core.proto.ImMessageResponse.Builder builderForValue) {
      if (responseBuilder_ == null) {
        body_ = builderForValue.build();
        onChanged();
      } else {
        responseBuilder_.setMessage(builderForValue.build());
      }
      bodyCase_ = 3;
      return this;
    }
    /**
     * <code>.ImMessageResponse response = 3;</code>
     */
    public Builder mergeResponse(com.hwl.im.core.proto.ImMessageResponse value) {
      if (responseBuilder_ == null) {
        if (bodyCase_ == 3 &&
            body_ != com.hwl.im.core.proto.ImMessageResponse.getDefaultInstance()) {
          body_ = com.hwl.im.core.proto.ImMessageResponse.newBuilder((com.hwl.im.core.proto.ImMessageResponse) body_)
              .mergeFrom(value).buildPartial();
        } else {
          body_ = value;
        }
        onChanged();
      } else {
        if (bodyCase_ == 3) {
          responseBuilder_.mergeFrom(value);
        }
        responseBuilder_.setMessage(value);
      }
      bodyCase_ = 3;
      return this;
    }
    /**
     * <code>.ImMessageResponse response = 3;</code>
     */
    public Builder clearResponse() {
      if (responseBuilder_ == null) {
        if (bodyCase_ == 3) {
          bodyCase_ = 0;
          body_ = null;
          onChanged();
        }
      } else {
        if (bodyCase_ == 3) {
          bodyCase_ = 0;
          body_ = null;
        }
        responseBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>.ImMessageResponse response = 3;</code>
     */
    public com.hwl.im.core.proto.ImMessageResponse.Builder getResponseBuilder() {
      return getResponseFieldBuilder().getBuilder();
    }
    /**
     * <code>.ImMessageResponse response = 3;</code>
     */
    public com.hwl.im.core.proto.ImMessageResponseOrBuilder getResponseOrBuilder() {
      if ((bodyCase_ == 3) && (responseBuilder_ != null)) {
        return responseBuilder_.getMessageOrBuilder();
      } else {
        if (bodyCase_ == 3) {
          return (com.hwl.im.core.proto.ImMessageResponse) body_;
        }
        return com.hwl.im.core.proto.ImMessageResponse.getDefaultInstance();
      }
    }
    /**
     * <code>.ImMessageResponse response = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.hwl.im.core.proto.ImMessageResponse, com.hwl.im.core.proto.ImMessageResponse.Builder, com.hwl.im.core.proto.ImMessageResponseOrBuilder> 
        getResponseFieldBuilder() {
      if (responseBuilder_ == null) {
        if (!(bodyCase_ == 3)) {
          body_ = com.hwl.im.core.proto.ImMessageResponse.getDefaultInstance();
        }
        responseBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.hwl.im.core.proto.ImMessageResponse, com.hwl.im.core.proto.ImMessageResponse.Builder, com.hwl.im.core.proto.ImMessageResponseOrBuilder>(
                (com.hwl.im.core.proto.ImMessageResponse) body_,
                getParentForChildren(),
                isClean());
        body_ = null;
      }
      bodyCase_ = 3;
      onChanged();;
      return responseBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:ImMessageContext)
  }

  // @@protoc_insertion_point(class_scope:ImMessageContext)
  private static final com.hwl.im.core.proto.ImMessageContext DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.hwl.im.core.proto.ImMessageContext();
  }

  public static com.hwl.im.core.proto.ImMessageContext getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ImMessageContext>
      PARSER = new com.google.protobuf.AbstractParser<ImMessageContext>() {
    public ImMessageContext parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ImMessageContext(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ImMessageContext> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ImMessageContext> getParserForType() {
    return PARSER;
  }

  public com.hwl.im.core.proto.ImMessageContext getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
