package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author ZYW
 * @date 2018/5/8
 */
@Service
public class VehicleDriveInBroadcastService {
    @Autowired
    private Account accountQueue;

    @Async
    public void receiv() {
        Queue queue = accountQueue.getQueue(Constants.CMQ_TOPIC_NSOP_CLOUD_MQ);
        try {
            while (true) {
                Message message = queue.receiveMessage(5);
                System.out.println("接收到的消息：" + message.msgBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
