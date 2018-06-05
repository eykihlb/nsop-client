package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.FTPConfig;
import com.mydao.nsop.client.config.InterFaceConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.util.HttpClientUtil;
import com.rabbitmq.client.Channel;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;

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

    /**
     * 监听拉取全量黑白名单请求
     */
    /*@RabbitListener(queues = {Constants.GET_BWLIST_QUEUE})
    public void getWBList(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        String uri = "";
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println("请求全量黑白名单");
            List<NameValuePair> list = new ArrayList<>();
            if (new String(message.getBody()).equals("black")){
                uri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_black();
                result = HttpClientUtil.sendHttpPostCall(uri,list);
                map = gson.fromJson(result,Map.class);
                Message msg = new Message(gson.toJson(map.get("data")).getBytes(),new MessageProperties());
                rabbitTemplate.send(Constants.FULL_BLACK_LIST,msg);
            }else{
                uri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_white();
                result = HttpClientUtil.sendHttpPostCall(uri,list);
                map = gson.fromJson(result,Map.class);
                Message msg = new Message(gson.toJson(map.get("data")).getBytes(),new MessageProperties());
                rabbitTemplate.send(Constants.FULL_WHITE_LIST,msg);
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }*/
    /**
     * 车辆驶入
     *//*
    @RabbitListener(queues = {Constants.ENTRY_QUEUE})
    public void entryQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶入");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.INTER_PARAM, new String(message.getBody(),"UTF-8")));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
        log.info("----------------------------------------------------- "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            System.out.println("删除消息");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }

    }


    *//**
     * 车辆驶入异常
     *//*
    @RabbitListener(queues = {Constants.ENTRY_EX_QUEUE})
    public void entryExQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶入异常");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.INTER_PARAM, new String(message.getBody(),"UTF-8")));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry_ex();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
        log.info("ENTRY_EX_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }

    *//**
     * 车辆驶入否认
     *//*
    @RabbitListener(queues = {Constants.ENTRY_DENY_QUEUE})
    public void entryDenyQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶入否认");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.INTER_PARAM, new String(message.getBody(),"UTF-8")));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry_ex();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
        log.info("ENTRY_DENY_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }

    *//**
     * 车辆驶入通行拒绝
     *//*
    @RabbitListener(queues = {Constants.PASS_REJECT_QUEUE})
    public void passRejectQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶入通行拒绝");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.INTER_PARAM, new String(message.getBody(),"UTF-8")));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry_ex();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
        log.info("PASS_REJECT_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }*/


    /**
     * 车辆驶出
     */
    /*@RabbitListener(queues = {Constants.EXIT_QUEUE})
    public void exitQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶出");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.EXIT_PARAM, new String(message.getBody(),"UTF-8")));
            String uri = trafficConfig.getUrl() + interFaceConfig.getExit();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
        log.info("EXIT_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }*/

    /**
     * 车辆驶出异常
     *//*
    @RabbitListener(queues = {Constants.EXIT_EX_QUEUE})
    public void exitExQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶出异常");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.EXIT_PARAM, new String(message.getBody(),"UTF-8")));
            String uri = trafficConfig.getUrl() + interFaceConfig.getExit_ex();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
        log.info("EXIT_EX_QUEUE "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
    }*/

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
        //fileName = map.get("key").toString();
        //fileName += map.get("key").toString();
        return fileName;
    }


}
