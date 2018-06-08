package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayWhiteListMapper;
import com.mydao.nsop.client.domain.entity.PayWhiteList;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.CMQServerException;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZYW
 * @date 2018/5/9
 */
@Service
public class VehicleWhiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleWhiteService.class);

    @Autowired
    private PayWhiteListMapper payWhiteListMapper;

    @Autowired
    private Account accountQueue;

    @Autowired
    private TrafficConfig trafficConfig;

    private Gson gson = new Gson();

    @Async
    public void addDelWhite() {
        Queue queue = accountQueue.getQueue(Constants.VEHICLE_WHITE_QUEUE + trafficConfig.getClientNum());
        PayWhiteList payWhiteList = new PayWhiteList();
        while(true) {
            try {
                List<Message> messageList = queue.batchReceiveMessage(10, 30);
                messageList.sort(Comparator.comparing((Message m) -> Integer.parseInt(m.msgBody.split("@@")[0] )) );
                for (Message msg : messageList) {
                    Map<String,Object> map = gson.fromJson(new String(msg.msgBody.split("@@")[2]),Map.class);
                    payWhiteList.setBand(map.get("band").toString());
                    payWhiteList.setBodycolor(map.get("bodycolor").toString());
                    payWhiteList.setPlatecolor(map.get("platecolor").toString());
                    payWhiteList.setPlateno(map.get("plateno").toString());
                    payWhiteList.setSubBand(map.get("sub_band").toString());
                    payWhiteList.setUptime((Date) map.get("uptime"));
                    payWhiteList.setVehclass(map.get("vehClass").toString());
                    if (msg.msgBody.split("@@")[1].equals("add_white")){
                        payWhiteListMapper.insertSelective(payWhiteList);
                    }else{
                        payWhiteListMapper.deleteByPrimaryKey(payWhiteList.getPlateno());
                    }
                }
            } catch (Exception e) {
                if(e instanceof CMQServerException) {
                    CMQServerException e1 = (CMQServerException) e;
                    LOGGER.error(e1.getErrorMessage());
                }
            }
        }
    }

    /*private void sendWhite(List<Message> messageList,Queue queue) {
        messageList.sort(Comparator.comparing((Message m) -> m.msgBody.split("@@")[0] ));
        MessageProperties mp = new MessageProperties();
        for (Message m : messageList) {
            mp.setContentType(m.msgBody.split("@@")[1]);
            org.springframework.amqp.core.Message msg = new org.springframework.amqp.core.Message(m.msgBody.split("@@")[2].getBytes(),mp);
            rabbitTemplate.send(Constants.TOPIC_TSX_WHITEVEH,msg);
        }
        try {
            queue.batchDeleteMessage(messageList.stream().map(item -> item.receiptHandle).collect(Collectors.toList()));
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }
    }*/
}
