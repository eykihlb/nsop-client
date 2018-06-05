package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.FTPConfig;
import com.mydao.nsop.client.config.InterFaceConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayEntryRecMapper;
import com.mydao.nsop.client.dao.PayExitRecMapper;
import com.mydao.nsop.client.domain.entity.PayEntryRec;
import com.mydao.nsop.client.domain.entity.PayExitRec;
import com.mydao.nsop.client.util.HttpClientUtil;
import com.rabbitmq.client.Channel;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
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
    private OAuth2RestTemplate oAuthRestTemplate;

    @Autowired
    private PayEntryRecMapper payEntryRecMapper;

    @Autowired
    private PayExitRecMapper payExitRecMapper;

    private Gson gson = new Gson();

    /**
     * 驶入
     */
    @Async
    public void driveIn(){
        Timer timer = new Timer(true);
        String url = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_white();
        timer.schedule(
            new java.util.TimerTask() { public void run() {
                List<PayEntryRec> perList = payEntryRecMapper.selectList();
                for (PayEntryRec payEntryRec : perList) {
                    ResponseEntity<Boolean> getEntity = oAuthRestTemplate.postForEntity(interFaceConfig.getEntry(),gson.toJson(payEntryRec),Boolean.class);
                    if (getEntity.getBody()){
                        payEntryRecMapper.updateById(payEntryRec.getRecid());
                    }
                }
                System.out.println("读取驶入记录");
            }},0,30*1000
        );
    }

    /**
     * 驶出
     */
    @Async
    public void driveOut(){
        Timer timer = new Timer(true);
        String url = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_white();
        timer.schedule(
                new java.util.TimerTask() { public void run() {
                    List<PayExitRec> perList = payExitRecMapper.selectList();
                    for (PayExitRec payExitRec : perList) {
                        ResponseEntity<Boolean> getEntity = oAuthRestTemplate.postForEntity(interFaceConfig.getExit(),gson.toJson(payExitRec),Boolean.class);
                        if (getEntity.getBody()){
                            payExitRecMapper.updateById(payExitRec.getRecid());
                        }
                    }
                    System.out.println("读取驶出记录");
                }},0,30*1000
        );
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
