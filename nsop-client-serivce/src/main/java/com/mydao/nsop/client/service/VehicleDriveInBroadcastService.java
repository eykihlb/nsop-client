package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayIssuedRecMapper;
import com.mydao.nsop.client.domain.entity.PayIssuedRec;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.CMQServerException;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZYW
 * @date 2018/5/8
 */
@Service
public class VehicleDriveInBroadcastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDriveInBroadcastService.class);

    @Autowired
    private Account accountQueue;

    @Autowired
    private TrafficConfig trafficConfig;

    @Autowired
    private PayIssuedRecMapper payIssuedRecMapper;

    private Gson gson = new Gson();

    @Async
    public void vehicleDriveIn() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        Queue queue = accountQueue.getQueue(Constants.VEHICLE_DRIVE_IN_QUEUE + trafficConfig.getClientNum());
        while(!Thread.interrupted()) {
            LOGGER.info("车辆驶入线程，时间：" + DateTime.now().toString("YYYY-MM-dd HH:mm:ss"));
            try {
                PayIssuedRec pir = new PayIssuedRec();
                PayIssuedRec payi = new PayIssuedRec();
                Map<String,Object> paramMap = new HashMap<>();
                Message message = queue.receiveMessage(15);
                System.out.println("接收到的驶入广播：" + message.msgBody);
                String messages = message.msgBody;
                if(StringUtils.isEmpty(messages)) {
                    LOGGER.warn("接收到的驶入消息为空！");
                    queue.deleteMessage(message.receiptHandle);
                    continue;
                }
                Map<String,Object> map = gson.fromJson(new String(message.msgBody),Map.class);
                String sd = sdf.format(new Date(Long.parseLong(String.valueOf(new BigDecimal(map.get("passTime").toString()).toPlainString()))));
                pir.setEntrytime(new Date(sd.replace("-","/")));
                pir.setLaneno(map.get("laneNo").toString());
                pir.setNetno(map.get("netNo").toString());
                pir.setPlatecolor(map.get("plateNo").toString().split("-")[1]);
                pir.setPlateno(map.get("plateNo").toString().split("-")[0]);
                pir.setRecid(map.get("entryRecId").toString());
                pir.setSiteno(map.get("siteNo").toString());
                pir.setVehclass("01");
                pir.setStatus("1");
                paramMap.put("status","1");
                paramMap.put("plateno",pir.getPlateno());
                /*payi = payIssuedRecMapper.selectById(pir.getPlateno());
                if (payi != null){
                    if (payIssuedRecMapper.updateByPlateNo(paramMap) > 0){
                        LOGGER.info("车牌号为："+message.msgBody+"的状态更新为驶入！");
                        queue.deleteMessage(message.receiptHandle);
                    }
                }else if (*/payIssuedRecMapper.insertSelective(pir);/*>0){*/
                    LOGGER.info("写入车牌号为："+pir.getPlateno()+"的驶入记录！");
                    queue.deleteMessage(message.receiptHandle);
                /*}*/
            } catch (Exception e) {
                if(e instanceof CMQServerException) {
                    CMQServerException e1 = (CMQServerException) e;
                    LOGGER.error(e1.getErrorMessage());
                } else {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

   /* private void sendVehicleDriveIn(Message message,Queue queue) {
        final CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        //发送车辆驶入信息
        rabbitTemplate.convertAndSend(Constants.VEHICLE_DRIVE_IN_LOCAL_QUEUE, "", message.msgBody, cd);
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
