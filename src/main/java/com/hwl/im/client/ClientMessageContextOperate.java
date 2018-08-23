// package com.hwl.im.client;

// import com.hwl.im.core.imaction.MessageListenExecutor;
// import com.hwl.im.core.imaction.MessageSendExecutor;
// import com.hwl.im.core.proto.ImMessageContext;

// import io.netty.channel.Channel;
// import io.netty.channel.ChannelFuture;
// import io.netty.channel.ChannelFutureListener;

// /**
//  * ClientMessageOperate operate=new ClientMessageOperate() case 1:
//  * operate.send(new UserValidateSend(),new UserValidteListener()) case 2:
//  * operate.send(new HeartBeatMessageSend()) case 3: operate.registerListener(new
//  * ChatUserMessageListener())
//  */
// public class ClientMessageContextOperate {

//     private Channel serverChannel;
//     private MessageSendExecutor sendExecutor;
//     private MessageListenExecutor listenExecutor;

//     public ClientMessageContextOperate(Channel channel) {
//         this.serverChannel = channel;
//     }

//     public ClientMessageContextOperate(Channel channel, MessageSendExecutor sendExecutor) {
//         this(channel);
//         this.sendExecutor = sendExecutor;
//     }

//     public ClientMessageContextOperate(Channel channel, MessageListenExecutor listenExecutor) {
//         this(channel);
//         this.listenExecutor = listenExecutor;
//     }

//     public ClientMessageContextOperate(Channel channel, MessageSendExecutor sendExecutor,
//             MessageListenExecutor listenExecutor) {
//         this(channel);
//         this.sendExecutor = sendExecutor;
//         this.listenExecutor = listenExecutor;
//     }

//     public void send() {
//         if (sendExecutor == null)
//             return;
//         ImMessageContext messageContext = sendExecutor.getMessageContext();
//         if (messageContext == null) {
//             sendExecutor.sendResultCallback(false);
//             return;
//         }
//         ChannelFuture channelFuture = serverChannel.writeAndFlush(messageContext);
//         channelFuture.addListener(new ChannelFutureListener() {

//             @Override
//             public void operationComplete(ChannelFuture future) throws Exception {
//                 if (sendExecutor.isSendFailedAndClose() && !future.isSuccess()) {
//                     serverChannel.close();
//                 }
//                 sendExecutor.sendResultCallback(future.isSuccess());
//             }
//         });
//     }

//     public void read(ImMessageContext messageContext) {
//         if (listenExecutor == null || messageContext == null)
//             return;

//         listenExecutor.execute(messageContext);
//         if (listenExecutor.executedAndClose() && serverChannel != null) {
//             serverChannel.close();
//         }
//     }
// }