package com.hwl.im.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.hwl.im.client.core.ClientMessageOperator;
import com.hwl.im.client.core.IMClientEngine;
import com.hwl.im.client.listen.ChatGroupMessageListen;
import com.hwl.im.client.listen.ChatUserMessageListen;
import com.hwl.im.client.listen.UserValidateListen;
import com.hwl.im.client.send.AddFriendMessageSend;
import com.hwl.im.client.send.ChatGroupMessageSend;
import com.hwl.im.client.send.ChatUserMessageSend;
import com.hwl.im.client.send.ClientAckMessageSend;
import com.hwl.im.client.send.UserValidateSend;
import com.hwl.imcore.improto.ImMessageType;

public class IMClient {

    private String host;
    private int port;
    private Long userId;
    private String userToken;

    public IMClient(String[] commandStr) {
        host = commandStr[1];
        port = Integer.parseInt(commandStr[2]);
        userId = Long.parseLong(commandStr[3]);
        userToken = commandStr[4];
    }

    public void connect() {
        ClientMessageOperator messageOperator = new ClientMessageOperator();
        messageOperator.registerListenExecutor(ImMessageType.ChatUser, new ChatUserMessageListen());
        messageOperator.registerListenExecutor(ImMessageType.ChatGroup, new ChatGroupMessageListen());
        messageOperator.setClientAckSender(msgId -> messageOperator.send(new ClientAckMessageSend(userId, msgId)));

        IMClientEngine im = new IMClientEngine(host, port);
        im.setMessageOperator(messageOperator);
        im.connect();

        if (im.isConnected()) {
            messageOperator.send(new UserValidateSend(userId, userToken), new UserValidateListen((sess) -> {
                sendCommandDesc();
                // messageOperator.send(new HeartBeatMessageSend());
            }));

            sendChatMessage(messageOperator);
        }
    }

    private void sendChatMessage(ClientMessageOperator messageOperator) {
        while (true) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String inputText = reader.readLine();
                if (inputText == null || inputText.trim() == "")
                    continue;

                String[] commands = inputText.split(" ");
                if (commands.length >= 4) {
                    if (commands[0].equals("send") && commands[1].equals("chatuser")) {
                        // send chatuser 2 nihao
                        messageOperator.send(new ChatUserMessageSend(userId, Long.parseLong(commands[2]), commands[3]));
                    } else if (commands[0].equals("send") && commands[1].equals("chatgroup")) {
                        messageOperator.send(new ChatGroupMessageSend(userId, commands[2], commands[3]));
                    } else if (commands[0].equals("send") && commands[1].equals("addfriend")) {
                        // send addfriend 2 woshi222
                        messageOperator.send(new AddFriendMessageSend(userId, "fromusername", "fromuserimage",
                                Long.parseLong(commands[2]), commands[3]));
                    } else if (commands[0].equals("send") && commands[1].equals("ack")) {
                        // send ack messageid
                        messageOperator.send(new ClientAckMessageSend(userId, "messageid"));
                    } else {
                        this.sendCommandDesc();
                    }
                } else {
                    this.sendCommandDesc();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                continue;
            }
        }
    }

    private void sendCommandDesc() {
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Send command format : send chatuser|chatgroup touserid|togroupguid content");
        System.out.println("ex : ");
        System.out.println("send chatuser 10000 contenttest1");
        System.out.println("send chatgroup groupguid groupcontenttest1");
        System.out.println("send addfriend 10000 woshi10000");
        System.out.println("----------------------------------------------------------------------------");

    }
}