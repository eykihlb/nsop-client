package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayIssuedRecMapper;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZYW
 * @date 2018/5/8
 */
@Service
public class VehicleDriveOutBroadcastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDriveOutBroadcastService.class);

    @Autowired
    private Account accountQueue;

    @Autowired
    private TrafficConfig trafficConfig;

    @Autowired
    private PayIssuedRecMapper payIssuedRecMapper;

    private Gson gson = new Gson();
    @Async
    public void vehicleDriveOut() {
        Queue queue = accountQueue.getQueue(Constants.VEHICLE_DRIVE_OUT_QUEUE + trafficConfig.getClientNum());
        while(true) {
            LOGGER.info("车辆驶出线程，时间：" + DateTime.now().toString("YYYY-MM-dd HH:mm:ss"));
            try {
                Message message = queue.receiveMessage(30);
                System.out.println("接收到的驶出广播：" + message.msgBody);
                Map<String,Object> map = new HashMap<>();
                Map<String,Object> paramMap = new HashMap<>();
                if(StringUtils.isEmpty(message.msgBody)) {
                    LOGGER.warn("接收到的驶出消息为空！");
                    queue.deleteMessage(message.receiptHandle);
                    continue;
                }
                map.put("status","2");
                map.put("plateno",message.msgBody);
                paramMap.put("status","1");
                paramMap.put("plateno",message.msgBody);
                if(payIssuedRecMapper.selectById(paramMap) != null){
                    payIssuedRecMapper.updateByPlateNo(map);
                    LOGGER.info("车牌号为："+message.msgBody+"的记录更新为驶出！");
                    queue.deleteMessage(message.receiptHandle);
                }else{
                    LOGGER.warn("未找到车牌号为："+message.msgBody+"的驶入记录！");
                    queue.deleteMessage(message.receiptHandle);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /*private void sendVehicleDriveIn(Message message,Queue queue) {
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
        *//*rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
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
        });*//*
    }*/
}
