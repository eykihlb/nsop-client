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
public class VehicleWhiteService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    public void addWhite() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String message = "添加白名单！";
        rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_WHITEVEH, Constants.ADD_WHITE_KEY, message, correlationData);
    }

    @Async
    public void delWhite() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String message = "删除白名单！";
        rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_WHITEVEH, Constants.DEL_WHITE_KEY, message, correlationData);
    }
}
