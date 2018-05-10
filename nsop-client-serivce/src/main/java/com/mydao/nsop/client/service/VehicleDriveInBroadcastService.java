package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
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
 * @date 2018/5/8
 */
@Service
public class VehicleDriveInBroadcastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDriveInBroadcastService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Account accountQueue;

    @Async
    public void vehicleDriveIn() {
        Queue queue = accountQueue.getQueue(Constants.VEHICLE_DRIVE_IN_QUEUE);
        while(true) {
            try {
                Message message = queue.receiveMessage(5);
                System.out.println("接收到的消息：" + message.msgBody);
                sendVehicleDriveIn(message,queue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendVehicleDriveIn(Message message,Queue queue) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //发送车辆驶入信息
        rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_BLACKVEH, Constants.ADD_BLACK_KEY, message.msgBody, correlationData);
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
