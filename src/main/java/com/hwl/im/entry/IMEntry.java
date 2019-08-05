//package com.hwl.im.entry;
//
//import com.hwl.im.IMConfig;
//
//import java.io.IOException;
//
//public class IMEntry {
//
//    /**
//     * windows :
//     * java -jar target\hwl-im-1.0.0-jar-with-dependencies.jar imserver 192.168.5.6 8081
//     * java -jar target\hwl-im-1.0.0-jar-with-dependencies.jar imserver 115.29.179.171 8017
//     * java -jar target\hwl-im-1.0.0-jar-with-dependencies.jar imclient 192.168.5.6 8081 9 001574da98db4a3024ada63f61f0788a
//     * <p>
//     * reset design :
//     * core / client / server / entry(console)
//     */
//
//    public static void main(String[] args) throws Exception {
//        if (args == null || args.length <= 0) {
////            errorDesc();
////            return;
//            args = new String[]{IMConfig.ENTRY_DEFAULT_IDENTITY, IMConfig.ENTRY_DEFAULT_HOST, IMConfig.ENTRY_DEFAULT_PORT};
////            args = new String[]{"imclient", "192.168.1.5", "8081", "6", "634d6c08c53c9e67c99b4e449aa5dd24"};
//        }
//
//        String firstCmd = args[0].toLowerCase();
//        if (firstCmd.equals(IMServerEntry.SERVER_FORMAT_PREFIX)) {
//            runServer(args);
//        } else if (firstCmd.equals(IMClientEntry.CLIENT_FORMAT_PREFIX)) {
//            runClient(args);
//        } else {
//            errorDesc();
//        }
//    }
//
//    static void errorDesc() {
//        System.out.println("im init command : ");
//        System.out.println(IMServerEntry.SERVER_FORMAT_DESCRIPTION + " / " + IMClientEntry.CLIENT_FORMAT_DESCRIPTION);
//    }
//
////    static void functionDesc() {
////        System.out.println("");
////    }
//
//    static void runServer(String[] args) throws InterruptedException {
//        IMServerEntry serverEntry = new IMServerEntry(args);
//        if (serverEntry.commandIsValid()) {
//            serverEntry.bind();
//        } else {
//            System.out.println("Serer entry error: " + serverEntry.getInvalidMessage());
//            errorDesc();
//        }
//    }
//
//    static void runClient(String[] args) throws IOException {
//        IMClientEntry clientEntry = new IMClientEntry(args);
//        if (clientEntry.commandIsValid()) {
//            clientEntry.connect();
//        } else {
//            System.out.println("Client entry error: " + clientEntry.getInvalidMessage());
//            errorDesc();
//        }
//    }
//}