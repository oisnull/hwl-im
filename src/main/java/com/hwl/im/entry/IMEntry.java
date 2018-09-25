package com.hwl.im.entry;

import java.io.IOException;

public class IMEntry {

    /**
     * windows :
     * java -jar hwl-im-1.0.0-jar-with-dependencies.jar imserver 127.0.0.1 8081
     * java -jar hwl-im-1.0.0-jar-with-dependencies.jar imclient 127.0.0.1 8081 10000 123456
     * <p>
     * reset design :
     * core / client / server / entry(console)
     */

    public static void main(String[] args) throws Exception {
        if (args == null || args.length <= 0) {
//            errorDesc();
//            return;
            args = new String[]{"imserver", "192.168.1.4", "8081"};
        }

        String firstCmd = args[0].toLowerCase();
        if (firstCmd.equals(IMServerEntry.SERVER_FORMAT_PREFIX)) {
            runServer(args);
        } else if (firstCmd.equals(IMClientEntry.CLIENT_FORMAT_PREFIX)) {
            runClient(args);
        } else {
            errorDesc();
        }
    }

    static void errorDesc() {
        System.out.println("im init command : ");
        System.out.println(IMServerEntry.SERVER_FORMAT_DESCRIPTION + " / " + IMClientEntry.CLIENT_FORMAT_DESCRIPTION);
    }

    static void functionDesc() {
        System.out.println("");
    }

    static void runServer(String[] args) throws InterruptedException {
        IMServerEntry serverEntry = new IMServerEntry(args);
        if (serverEntry.commandIsValid()) {
            serverEntry.bind();
        } else {
            System.out.println("Serer entry error: " + serverEntry.getInvalidMessage());
            errorDesc();
        }
    }

    static void runClient(String[] args) throws IOException {
        IMClientEntry clientEntry = new IMClientEntry(args);
        if (clientEntry.commandIsValid()) {
            clientEntry.connect();
        } else {
            System.out.println("Client entry error: " + clientEntry.getInvalidMessage());
            errorDesc();
        }
    }
}