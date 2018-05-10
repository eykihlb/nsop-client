package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ZYW
 * @date 2018/5/7
 */
@Component
public class VehicleDriveInOutService {

    private static final Logger log = LoggerFactory.getLogger(VehicleDriveInOutService.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

   /* @Async
    public void test(){
        *//*try {
            while (true) {
                Thread.sleep(1000);
                System.out.println("test-------------------");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*//*
    }

    *//**
     * 发送消息
     *//*
    @Async
    public void test2() {
        System.out.println("Sender : Hello Word！");
        for(int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_JOURNEY,"Hello Word！" + i);
        }

    }

    *//**
     * 接收消息
     *//*
    @Async
    public void test3() {
//        while(true) {
//            Message receive = rabbitTemplate.receive(Constants.TOPIC_TSX_JOURNEY,-1);
//            String s = new String(receive.getBody());
//            System.out.println("接收：" + s);
//        }
    }*/



    @RabbitListener(queues = {Constants.ENTRY_QUEUE})
    public void entryQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("FANOUT_QUEUE_A "+new String(message.getBody()));
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
    }


    @RabbitListener(queues = {Constants.ENTRY_EX_QUEUE})
    public void entryExQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("FANOUT_QUEUE_A "+new String(message.getBody()));
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
    }


    @RabbitListener(queues = {Constants.ENTRY_DENY_QUEUE})
    public void entryDenyQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("FANOUT_QUEUE_A "+new String(message.getBody()));
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
    }

    @RabbitListener(queues = {Constants.PASS_REJECT_QUEUE})
    public void passRejectQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("FANOUT_QUEUE_A "+new String(message.getBody()));
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
    }


    @RabbitListener(queues = {Constants.EXIT_QUEUE})
    public void exitQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("FANOUT_QUEUE_A "+new String(message.getBody()));
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
    }

    @RabbitListener(queues = {Constants.EXIT_EX_QUEUE})
    public void exitExQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("FANOUT_QUEUE_A "+new String(message.getBody()));
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
    }
}
