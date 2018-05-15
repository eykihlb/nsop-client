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
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
     * 车辆驶入
     */
    @RabbitListener(queues = {Constants.ENTRY_QUEUE})
    public void entryQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶入");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.ENTRY, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }
        log.info("----------------------------------------------------- "+new String(message.getBody()));
        if ("200".equals(result)){//删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }else{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
        }

    }


    /**
     * 车辆驶入异常
     */
    @RabbitListener(queues = {Constants.ENTRY_EX_QUEUE})
    public void entryExQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶入异常");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.ENTRY_EX, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry_ex();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
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


    /**
     * 车辆驶入否认
     */
    @RabbitListener(queues = {Constants.ENTRY_DENY_QUEUE})
    public void entryDenyQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶入否认");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.ENTRY_DENY, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry_deny();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
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

    /**
     * 车辆驶入通行拒绝
     */
    @RabbitListener(queues = {Constants.PASS_REJECT_QUEUE})
    public void passRejectQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶入通行拒绝");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.PASS_REJECT, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getPass_reject();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
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
    public void exitQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶出");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.EXIT, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getEntry();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
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
    public void exitExQueue(Message message, Channel channel) throws Exception {
        channel.basicQos(1);
        String result = "";
        try {
            System.out.println("车辆驶出异常");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair(Constants.EXIT_EX, new String(message.getBody())));
            String uri = trafficConfig.getUrl() + interFaceConfig.getExit_ex();
            result = httpBackCode(HttpClientUtil.sendHttpPostCall(uri,list));
            //异步文件上传
            //fileUploadService.fileUpload(fTPConfig,getFileName(new String(message.getBody())));
        } catch (Exception e) {
            log.error(e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,true);
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
        //fileName = map.get("key").toString();
        //fileName += map.get("key").toString();
        return fileName;
    }


}
