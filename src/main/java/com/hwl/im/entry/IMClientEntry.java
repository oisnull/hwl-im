package com.hwl.im.entry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TimerTask;

import com.hwl.im.client.ClientMessageOperate;
import com.hwl.im.client.IMClientHeartbeatTimer;
import com.hwl.im.client.IMClientLauncher;
import com.hwl.im.client.listen.ChatGroupMessageListen;
import com.hwl.im.client.listen.ChatUserMessageListen;
import com.hwl.im.client.listen.UserValidateListen;
import com.hwl.im.client.send.ChatGroupMessageSend;
import com.hwl.im.client.send.ChatUserMessageSend;
import com.hwl.im.client.send.HeartBeatMessageSend;
import com.hwl.im.client.send.UserValidateSend;
import com.hwl.im.core.immode.MessageRequestHeadOperate;
import com.hwl.im.core.proto.ImMessageType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IMClientEntry {

    static Logger log = LogManager.getLogger(IMClientEntry.class.getName());

    public final static String CLIENT_FORMAT_PREFIX = "imclient";
    public final static String CLIENT_FORMAT_DESCRIPTION = "imclient localhost port user_id user_token";
    public final static int CLIENT_FORMAT_LENGTH = 5;

    private String[] commandStr;
    private boolean isValid = true;
    private String invalidMessage = null;

    private String host;
    private int port;
    private Long userId;
    private String userToken;

    public IMClientEntry(String[] commandStr) {
        this.commandStr = commandStr;

        check();
    }

    private void check() {
        if (commandStr == null || commandStr.length < CLIENT_FORMAT_LENGTH) {
            isValid = false;
            invalidMessage = "command string format error";
            return;
        }
        if (commandStr[0].equals(CLIENT_FORMAT_PREFIX)) {
            host = commandStr[1];
            port = Integer.parseInt(commandStr[2]);
            userId = Long.parseLong(commandStr[3]);
            userToken = commandStr[4];
            isValid = true;
            invalidMessage = null;
        } else {
            isValid = false;
            invalidMessage = "command string parse error";
            return;
        }
    }

    public boolean commandIsValid() {
        return this.isValid;
    }

    public String getInvalidMessage() {
        return this.invalidMessage;
    }

    public void connect() throws IOException {
        ClientMessageOperate messageOperate = new ClientMessageOperate();
        messageOperate.registerListenExecutor(ImMessageType.ChatUser, new ChatUserMessageListen());
        messageOperate.registerListenExecutor(ImMessageType.ChatGroup, new ChatGroupMessageListen());

        IMClientLauncher launcher = new IMClientLauncher(host, port);
        launcher.registerAction(messageOperate);
        launcher.connect();

        messageOperate.send(new UserValidateSend(userId, userToken), new UserValidateListen((sessionid) -> {
            MessageRequestHeadOperate.setSessionid(sessionid);
            IMClientHeartbeatTimer.getInstance().run(new TimerTask() {
                @Override
                public void run() {
                    messageOperate.send(new HeartBeatMessageSend());
                }
            });
            return null;
        }, (msg) -> {
            launcher.stop();
            return null;
        }));

        this.sendCommandDesc();

        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String inputText = reader.readLine();
            if (inputText != null && !inputText.isEmpty()) {
                String[] commands = inputText.split(" ");
                if (commands.length >= 4) {
                    if (commands[0].equals("send") && commands[1].equals("chatuser")) {
                        messageOperate.send(new ChatUserMessageSend(userId, Long.parseLong(commands[2]), commands[3]));
                    } else if (commands[0].equals("send") && commands[1].equals("chatgroup")) {
                        messageOperate.send(new ChatGroupMessageSend(userId, commands[2], commands[3]));
                    } else {
                        this.sendCommandDesc();
                    }
                } else {
                    this.sendCommandDesc();
                }
            } else {
                System.out.println("None command");
                this.sendCommandDesc();
            }
        }
    }

    private void sendCommandDesc() {
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Send command format : send chatuser|chatgroup touserid|togroupguid content");
        System.out.println("ex : ");
        System.out.println("send chatuser 10000 contenttest1");
        System.out.println("send chatgroup groupguid groupcontenttest1");
        System.out.println("----------------------------------------------------------------------------");

    }
}