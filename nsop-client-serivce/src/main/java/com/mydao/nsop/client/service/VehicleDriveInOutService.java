package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.util.FTPUtil;
import com.mydao.nsop.client.util.HttpClientUtil;
import com.qcloud.cmq.Json.JSONObject;
import com.rabbitmq.client.Channel;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ZYW
 * @date 2018/5/7
 */
@Component
public class VehicleDriveInOutService {

    private static final Logger log = LoggerFactory.getLogger(VehicleDriveInOutService.class);

    @Value("${nsop.traffic.url}")
    private String URL;
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



    /**
     * 车辆驶入
     */
    @RabbitListener(queues = {Constants.ENTRY_QUEUE})
    public void entryQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        String result = "";
        try {
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("entryInfo", message.getBody().toString()));
            String uri = URL + "roadEntry/normalDriveInto";
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("FANOUT_QUEUE_A "+new String(message.getBody()));
        if ("200".equals(result)){
            //数据处理成功，完成图片下载上传，通知MQ删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }

    }


    /**
     * 车辆驶入异常
     */
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


    /**
     * 车辆驶入否认
     */
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

    /**
     * 车辆驶入通行拒绝
     */
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


    /**
     * 车辆驶出
     */
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

    /**
     * 车辆驶出异常
     */
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

    /**
     * 处理Http返回值
     */
    private String httpBackCode(String result){
        Gson gson = new Gson();
        Map<String,Object> map = gson.fromJson(result,Map.class);
        return  String.valueOf(Double.valueOf(map.get("code").toString()).intValue());
    }
}
