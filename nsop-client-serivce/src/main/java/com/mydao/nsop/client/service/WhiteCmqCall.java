package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class WhiteCmqCall implements Callable<List<Message>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WhiteCmqCall.class);
    private Account account;
    private String  clientNum;

    public WhiteCmqCall(Account account, String clientNum){
        this.account = account;
        this.clientNum = clientNum;
    }

    @Override
    public List<Message> call() throws Exception {
        LOGGER.info("CMQ白名单请求线程");
        Queue queue = account.getQueue(Constants.VEHICLE_WHITE_QUEUE + clientNum);
        List<Message> messageList = queue.batchReceiveMessage(10, 15);
        return messageList;
    }
}
