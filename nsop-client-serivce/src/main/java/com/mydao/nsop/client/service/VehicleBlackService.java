package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
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
public class VehicleBlackService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    public void addBlack() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String message = "添加黑名单！";
        rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_BLACKVEH, Constants.ADD_BLACK_KEY, message, correlationData);
    }

    @Async
    public void delBlack() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String message = "删除黑名单！";
        rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_BLACKVEH, Constants.DEL_BLACK_KEY, message, correlationData);
    }

}
