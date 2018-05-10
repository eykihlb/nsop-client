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

import java.util.UUID;

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
                Message message = queue.receiveMessage(30);
                sendWhite(message,queue);
            } catch (Exception e) {
                if(e instanceof CMQServerException) {
                    CMQServerException e1 = (CMQServerException) e;
                    LOGGER.error(e1.getErrorMessage());
                }
            }
        }
    }

    private void sendWhite(Message message,Queue queue) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //根据消息发送到新增白名单和删除白名单中
        if(message.msgTag.contains("aaa")) {
            rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_WHITEVEH, Constants.ADD_WHITE_KEY, message.msgBody, correlationData);
        } else {
            rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_WHITEVEH, Constants.DEL_WHITE_KEY, message.msgBody, correlationData);
        }

        //如果成功 删除消息
        try {
            queue.deleteMessage(message.receiptHandle);
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }
    }
}
