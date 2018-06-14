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
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        int flag = 0;
        while(true) {
            try {
                List<Message> messageList = queue.batchReceiveMessage(10, 30);
                messageList.sort(Comparator.comparing((Message m) -> Integer.parseInt(m.msgBody.split("@@")[0] )) );
                for (Message msg : messageList) {
                    System.out.println("接收到的黑名单："+msg.msgBody);
                    Map<String,Object> map = gson.fromJson(new String(msg.msgBody.split("@@")[2]),Map.class);
                    payBlackList.setBand("暂无");
                    payBlackList.setBodycolor("00");
                    payBlackList.setPlatecolor("00");
                    payBlackList.setPlateno(map.get("plateNo").toString());
                    payBlackList.setSubBand("暂无");
                    payBlackList.setUptime(new Date());
                    payBlackList.setVehclass("01");
                    if (msg.msgBody.split("@@")[1].equals("add_black")){
                        flag = payBlackListMapper.insertSelective(payBlackList);
                    }else{
                        flag = payBlackListMapper.deleteByPrimaryKey(payBlackList.getPlateno());
                    }
                    //新增/删除黑名单记录成功
                    if (flag > 0){
                        //删除消息
                        queue.deleteMessage(msg.receiptHandle);
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
