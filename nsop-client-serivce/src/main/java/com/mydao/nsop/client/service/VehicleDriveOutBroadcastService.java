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
 * @date 2018/5/8
 */
@Service
public class VehicleDriveOutBroadcastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDriveOutBroadcastService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Account accountQueue;

    @Autowired
    private TrafficConfig trafficConfig;

    @Async
    public void vehicleDriveOut() {
        Queue queue = accountQueue.getQueue(Constants.VEHICLE_DRIVE_OUT_QUEUE + trafficConfig.getClientNum());
        while(true) {
            try {
                Message message = queue.receiveMessage(30);
                System.out.println("接收到的消息：" + message.msgBody);
                sendVehicleDriveIn(message,queue);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private void sendVehicleDriveIn(Message message,Queue queue) {
        final CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        //发送车辆驶入信息
        rabbitTemplate.convertAndSend(Constants.VEHICLE_DRIVE_OUT_LOCAL_QUEUE, "", message.msgBody, cd);
        try {
            queue.deleteMessage(message.receiptHandle);
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }
        /*rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(ack) {
                //如果成功 删除消息
                try {
                    queue.deleteMessage(message.receiptHandle);
                } catch (Exception e) {
                    if(e instanceof CMQServerException) {
                        CMQServerException e1 = (CMQServerException) e;
                        LOGGER.error(e1.getErrorMessage());
                    }
                }
            } else {
                LOGGER.info("消息发送到exchange失败,原因: {}", cause);
            }
        });*/
    }
}
