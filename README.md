# hwl-im
java (netty,protobuf3)

### How useage? ###
**Sever step:**
```
1, Definition request model and response model in protocol file(file path is "src\main\java\com\hwl\im\protocol\im_message.proto")
2, Use "protoc --java_out=src/main/java src/main/java/com/hwl/im/protocol/im_message.proto" command and generate protocol code file(code directory path is "src\main\java\com\hwl\im\core\proto")
3, Definition business class and inherit AbstractMessageReceivExecutor<TRequest> class in business directory (business directory path is "src\main\java\com\hwl\im\server\receive")
```

**Client step:**
```
1, Definition send message business class and inherit AbstractMessageSendExecutor class in send business directory (path is "src\main\java\com\hwl\im\client\send")
2, Or definition listen message business class and inherit AbstractMessageListenExecutor<TResponse> class in listen business directory (path is "src\main\java\com\hwl\im\client\listen")
```
