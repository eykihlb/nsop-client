package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayBlackListMapper;
import com.mydao.nsop.client.domain.entity.PayBlackList;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.CMQServerException;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ZYW
 * @date 2018/5/9
 */
@Service
public class VehicleBlackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleBlackService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Account accountQueue;

    @Autowired
    private TrafficConfig trafficConfig;

    @Autowired
    private PayBlackListMapper payBlackListMapper;

    private Gson gson = new Gson();

    @Async
    public void addDelBlack() {
        Queue queue = accountQueue.getQueue(Constants.VEHICLE_BLACK_QUEUE + trafficConfig.getClientNum());
        PayBlackList payBlackList = new PayBlackList();
        while(true) {
            try {
                List<Message> messageList = queue.batchReceiveMessage(10, 30);
                messageList.sort(Comparator.comparing((Message m) -> Integer.parseInt(m.msgBody.split("@@")[0] )) );
                for (Message msg : messageList) {
                    Map<String,Object> map = gson.fromJson(new String(msg.msgBody.split("@@")[2]),Map.class);
                    payBlackList.setBand(map.get("band").toString());
                    payBlackList.setBodycolor(map.get("bodycolor").toString());
                    payBlackList.setPlatecolor(map.get("platecolor").toString());
                    payBlackList.setPlateno(map.get("plateno").toString());
                    payBlackList.setSubBand(map.get("sub_band").toString());
                    payBlackList.setUptime((Date) map.get("uptime"));
                    payBlackList.setVehclass(map.get("vehClass").toString());
                    if (msg.msgBody.split("@@")[1].equals("")){
                        payBlackListMapper.insertSelective(payBlackList);
                    }else{
                        payBlackListMapper.deleteByPrimaryKey(payBlackList.getPlateno());
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

    /*private void sendBlack(List<Message> messageList,Queue queue) {
        messageList.sort(Comparator.comparing((Message m) -> Integer.parseInt(m.msgBody.split("@@")[0] )) );
        MessageProperties mp = new MessageProperties();
        for (Message m : messageList) {
            System.out.println(m.msgBody.split("@@")[0] +"---------------"+m.msgId);
            mp.setContentType(m.msgBody.split("@@")[1]);
            org.springframework.amqp.core.Message msg = new org.springframework.amqp.core.Message(m.msgBody.split("@@")[2].getBytes(),mp);
            rabbitTemplate.send(Constants.TOPIC_TSX_BLACKVEH,msg);
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
