package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.TrafficConfig;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.CMQServerException;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author ZYW
 * @date 2018/5/9
 */
@Service
public class VehicleBlackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleBlackService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Account accountQueue;

    @Autowired
    private TrafficConfig trafficConfig;

    @Async
    public void addDelBlack() {
        Queue queue = accountQueue.getQueue(Constants.VEHICLE_BLACK_QUEUE + trafficConfig.getClientNum());
        while(true) {
            try {
                List<Message> messageList = queue.batchReceiveMessage(10, 30);
                sendBlack(messageList,queue);
            } catch (Exception e) {
                if(e instanceof CMQServerException) {
                    CMQServerException e1 = (CMQServerException) e;
                    LOGGER.error(e1.getErrorMessage());
                }
            }
        }
    }

    private void sendBlack(List<Message> messageList,Queue queue) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        /*if(message.msgTag.contains("aaa")) {
            rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_BLACKVEH, Constants.ADD_BLACK_KEY, message.msgBody, correlationData);
        } else {
            rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_BLACKVEH, Constants.DEL_BLACK_KEY, message.msgBody, correlationData);
        }

        //如果成功 删除消息
        try {
            queue.deleteMessage(message.receiptHandle);
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }*/
    }

}
