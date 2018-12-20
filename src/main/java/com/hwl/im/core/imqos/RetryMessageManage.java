//package com.hwl.im.core.imqos;
//
//import java.util.LinkedList;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.function.Consumer;
//
//import com.hwl.im.core.ImCoreConfig;
//import com.hwl.im.core.immode.MessageOperate;
//import com.hwl.imcore.improto.ImMessageContext;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//public class RetryMessageManage {
//
//    static Logger log = LogManager.getLogger(RetryMessageManage.class.getName());
//
//    private static LinkedList<RetryMessageModel> messageContainer = new LinkedList<>();
//    private static RetryMessageManage instance = null;
//    private static Timer timer;
//    private static final int TIMER_CHECH_INTERVAL = 3 * 60 * 1000; // 1min
//
//    private boolean isRuning = false;
//
//    public static RetryMessageManage getInstance() {
//        if (instance == null)
//            instance = new RetryMessageManage();
//
//        return instance;
//    }
//
//    private void exec() {
//        if (isRuning)
//            return;
//        isRuning = true;
//
//        RetryMessageModel retryMessageModel = messageContainer.poll();
//        if (retryMessageModel == null) {
//            isRuning = false;
//            log.debug("Retry message completed, no data in queue ...");
//            return;
//        }
//
//        if (retryMessageModel.retryCount > ImCoreConfig.MESSAGE_SEND_FAILED_RETRY_COUNT) {
//            log.error("The message sent to {} is retried {} times and will be discarded , message content : {}",
//                    retryMessageModel.userid, retryMessageModel.retryCount,
//                    retryMessageModel.messageContext.toString());
//
//            isRuning = false;
//            exec();
//            return;
//        }
//
////        MessageOperate.serverSendAndRetry(retryMessageModel.userid, retryMessageModel.messageContext, new Consumer<Boolean>() {
////
////            @Override
////            public void accept(Boolean succ) {
////                if (succ == false) {
////                    retryMessageModel.retryCount++;
////                    retryMessageModel.currentTimeMills = System.currentTimeMillis();
////                    messageContainer.add(retryMessageModel);
////                }
////
////                isRuning = false;
////                exec();
////            }
////        });
//    }
//
//    public void startup() {
//        log.debug("Retry message run start ...");
//        stop();
//
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                exec();
//            }
//        }, 0, TIMER_CHECH_INTERVAL);
//    }
//
//    public void stop() {
//        if (timer != null) {
//            try {
//                timer.cancel();
//            } finally {
//                timer = null;
//            }
//        }
//    }
//
//    public void addMessage(Long userid, ImMessageContext messageContext,boolean isAddFirst) {
//        if (userid <= 0 || messageContext == null)
//            return;
//
//        log.error("The message sent to {} is added to the retrial queue , message content : {}", userid,
//                messageContext.toString());
//        RetryMessageModel messageModel = new RetryMessageModel();
//        messageModel.userid = userid;
//        messageModel.messageContext = messageContext;
//        messageModel.retryCount = 0;
//        messageModel.currentTimeMills = 0L;
//        if(isAddFirst){
//            messageContainer.addFirst(messageModel);
//        }else{
//            messageContainer.add(messageModel);
//        }
//    }
//
//    // public void removeMessage(Long userid) {
//    // if (userid <= 0)
//    // return;
//
//    // messageContainer.removeIf(f -> f.userid.equals(userid));
//    // }
//
//    private class RetryMessageModel {
//        Long userid;
//        ImMessageContext messageContext;
//        int retryCount;
//        Long currentTimeMills;
//    }
//}