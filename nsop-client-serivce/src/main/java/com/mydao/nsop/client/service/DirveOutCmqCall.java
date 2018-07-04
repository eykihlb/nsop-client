package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class DirveOutCmqCall implements Callable<Message> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirveOutCmqCall.class);
    private Account account;
    private String  clientNum;

    public DirveOutCmqCall(Account account, String clientNum){
        this.account = account;
        this.clientNum = clientNum;
    }

    @Override
    public Message call() throws Exception {
        LOGGER.info("CMQ驶出请求线程");
        Queue queue = account.getQueue(Constants.VEHICLE_DRIVE_OUT_QUEUE + clientNum);
        Message message = queue.receiveMessage(15);
        return message;
    }
}
