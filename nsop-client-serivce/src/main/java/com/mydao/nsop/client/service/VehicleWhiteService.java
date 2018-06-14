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
        int flag = 0;
        while(true) {
            try {
                List<Message> messageList = queue.batchReceiveMessage(10, 30);
                messageList.sort(Comparator.comparing((Message m) -> Integer.parseInt(m.msgBody.split("@@")[0] )) );
                for (Message msg : messageList) {
                    System.out.println("接收到的白名单："+msg.msgBody);
                    Map<String,Object> map = gson.fromJson(new String(msg.msgBody.split("@@")[2]),Map.class);
                    payWhiteList.setBand("暂无");
                    payWhiteList.setBodycolor("00");
                    payWhiteList.setPlatecolor("00");
                    payWhiteList.setPlateno(map.get("plateNo").toString());
                    payWhiteList.setSubBand("暂无");
                    payWhiteList.setUptime(new Date());
                    payWhiteList.setVehclass("01");
                    if (msg.msgBody.split("@@")[1].equals("add_white")){
                        flag = payWhiteListMapper.insertSelective(payWhiteList);
                    }else{
                        flag = payWhiteListMapper.deleteByPrimaryKey(payWhiteList.getPlateno());
                    }
                    //新增/删除白名单记录成功
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
}
