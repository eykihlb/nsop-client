package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.FTPConfig;
import com.mydao.nsop.client.config.InterFaceConfig;
import com.mydao.nsop.client.config.TrafficConfig;
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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ZYW
 * @date 2018/5/7
 */
@Component
public class VehicleDriveInOutService {

    private static final Logger log = LoggerFactory.getLogger(VehicleDriveInOutService.class);
    @Autowired
    private FTPConfig fTPConfig;
    @Autowired
    private InterFaceConfig interFaceConfig;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private TrafficConfig trafficConfig;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    private Gson gson = new Gson();

    /*@Async
    public void test2() {
        System.out.println("Sender : Hello Word！");
        for(int i = 0; i < 10; i++) {
            System.out.println("message" + i);
            rabbitTemplate.convertAndSend(Constants.ENTRY_QUEUE,"{'laneNo':'65000115E0','passTime':'445555555','plateNo':'京A12345-1','src':'00','feature':'00_00_00','cameraId':'000001','passSeq':'2000','clientId':'000001','clientSeq':'1'}");
        }

    }*/

    /**
     * 车辆驶入
     */
    @RabbitListener(queues = {Constants.ENTRY_QUEUE})
    public void entryQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        String result = "";
        try {
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.ENTRY, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("ENTRY_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            System.out.println("删除消息");
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
        String result = "";
        try {
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.ENTRY_EX, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry_ex();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("ENTRY_EX_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }


    /**
     * 车辆驶入否认
     */
    @RabbitListener(queues = {Constants.ENTRY_DENY_QUEUE})
    public void entryDenyQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        String result = "";
        try {
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.ENTRY_DENY, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry_deny();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("ENTRY_DENY_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }

    /**
     * 车辆驶入通行拒绝
     */
    @RabbitListener(queues = {Constants.PASS_REJECT_QUEUE})
    public void passRejectQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        String result = "";
        try {
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.PASS_REJECT, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getPass_reject();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("PASS_REJECT_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }


    /**
     * 车辆驶出
     */
    @RabbitListener(queues = {Constants.EXIT_QUEUE})
    public void exitQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        String result = "";
        try {
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.EXIT, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getExit();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("EXIT_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }

    /**
     * 车辆驶出异常
     */
    @RabbitListener(queues = {Constants.EXIT_EX_QUEUE})
    public void exitExQueue(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        String result = "";
        try {
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.EXIT_EX, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getExit_ex();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("EXIT_EX_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }

    /**
     * 处理Http返回值
     */
    private String httpBackCode(String result){
        Map<String,Object> map = gson.fromJson(result,Map.class);
        return  String.valueOf(Double.valueOf(map.get("code").toString()).intValue());
    }

    /**
     * 获取文件名
     */
    private String getFileName(String msg){
        String fileName = "";
        Map<String,Object> map = gson.fromJson(msg,Map.class);
        fileName = map.get("key").toString();
        fileName += map.get("key").toString();
        return fileName;
    }


}
