package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class BlcakCmqCall implements Callable<List<Message>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlcakCmqCall.class);
    private Account account;
    private String  clientNum;

    public BlcakCmqCall(Account account,String clientNum){
        this.account = account;
        this.clientNum = clientNum;
    }

    @Override
    public List<Message> call() throws Exception {
        LOGGER.info("CMQ黑名单请求线程");
        Queue queue = account.getQueue(Constants.VEHICLE_BLACK_QUEUE + clientNum);
        List<Message> messageList = queue.batchReceiveMessage(10, 15);
        return messageList;
    }
}
