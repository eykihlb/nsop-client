package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author ZYW
 * @date 2018/5/7
 */
@Component
public class VehicleDriveInService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Async
    public void test(){
        /*try {
            while (true) {
                Thread.sleep(1000);
                System.out.println("test-------------------");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 发送消息
     */
    @Async
    public void test2() {
        System.out.println("Sender : Hello Word！");
        for(int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(Constants.TOPIC_TSX_JOURNEY,"Hello Word！" + i);
        }

    }

    /**
     * 接收消息
     */
    @Async
    public void test3() {
//        while(true) {
//            Message receive = rabbitTemplate.receive(Constants.TOPIC_TSX_JOURNEY,-1);
//            String s = new String(receive.getBody());
//            System.out.println("接收：" + s);
//        }
    }
}
