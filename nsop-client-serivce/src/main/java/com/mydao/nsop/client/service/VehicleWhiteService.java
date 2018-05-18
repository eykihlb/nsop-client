package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.TrafficConfig;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.CMQServerException;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author ZYW
 * @date 2018/5/9
 */
@Service
public class VehicleWhiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleWhiteService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Account accountQueue;

    @Autowired
    private TrafficConfig trafficConfig;

    @Async
    public void addDelWhite() {
        Queue queue = accountQueue.getQueue(Constants.VEHICLE_WHITE_QUEUE + trafficConfig.getClientNum());
        while(true) {
            try {
                List<Message> messageList = queue.batchReceiveMessage(10, 30);
                sendWhite(messageList,queue);
            } catch (Exception e) {
                if(e instanceof CMQServerException) {
                    CMQServerException e1 = (CMQServerException) e;
                    LOGGER.error(e1.getErrorMessage());
                }
            }
        }
    }

    private void sendWhite(List<Message> messageList,Queue queue) {
        messageList.sort(Comparator.comparing((Message m) -> m.msgBody.split("@@")[0] ));
        MessageProperties mp = new MessageProperties();
        for (Message m : messageList) {
            mp.setContentType(m.msgBody.split("@@")[1]);
            org.springframework.amqp.core.Message msg = new org.springframework.amqp.core.Message(m.msgBody.split("@@")[2].getBytes(),mp);
            rabbitTemplate.send(Constants.TOPIC_TSX_WHITEVEH,msg);
        }
        try {
            queue.batchDeleteMessage(messageList.stream().map(item -> item.receiptHandle).collect(Collectors.toList()));
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }
    }
}
