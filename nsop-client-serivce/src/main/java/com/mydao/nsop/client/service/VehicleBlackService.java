package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.CMQConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayBlackListMapper;
import com.mydao.nsop.client.domain.entity.PayBlackList;
import com.mydao.nsop.client.monitor.MonitorThread;
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

import java.util.*;
import java.util.concurrent.*;

/**
 * @author ZYW
 * @date 2018/5/9
 */
@Service
public class VehicleBlackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleBlackService.class);

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void setExecutorService(ExecutorService executorService) {
        VehicleBlackService.executorService = executorService;
    }

    @Autowired
    private CMQConfig config;

    @Autowired
    private TrafficConfig trafficConfig;

    @Autowired
    private PayBlackListMapper payBlackListMapper;

    private Gson gson = new Gson();

    @Async
    public void addDelBlack() {

        int flag = 0;
        while(!Thread.interrupted()) {
            LOGGER.info("黑名单线程，时间：" + DateTime.now().toString("YYYY-MM-dd HH:mm:ss"));
            Future<List<Message>> futureTask = null ;
            try {
                Account accountQueue = config.accountQueue();
                Queue queue = accountQueue.getQueue(Constants.VEHICLE_BLACK_QUEUE + trafficConfig.getClientNum());
                PayBlackList payBlackList = new PayBlackList();
                futureTask = executorService.submit(new BlcakCmqCall(accountQueue,trafficConfig.getClientNum()));
                List<Message> messageList = futureTask.get(20, TimeUnit.SECONDS);
                messageList.sort(Comparator.comparing((Message m) -> Integer.parseInt(m.msgBody.split("@@")[0] )) );
                for (Message msg : messageList) {
                    LOGGER.info("接收到的黑名单："+msg.msgBody);
                    String message = msg.msgBody.split("@@")[2];
                    if(StringUtils.isEmpty(message)) {
                        LOGGER.warn("接收到的主题消息为空！");
                        queue.deleteMessage(msg.receiptHandle);
                        continue;
                    }
                    Map<String,Object> map = gson.fromJson(new String(message),Map.class);
                    payBlackList.setBand(map.get("band").toString());
                    payBlackList.setBodycolor(map.get("bodycolor").toString());
                    payBlackList.setPlatecolor(map.get("platecolor").toString());
                    payBlackList.setPlateno(map.get("plateno").toString());
                    payBlackList.setSubBand(map.get("subBand").toString());
                    payBlackList.setUptime(new Date());
                    payBlackList.setVehclass(Objects.toString(map.get("vehclass"),"0"));
                    int count = payBlackListMapper.selectByPlateNo(payBlackList.getPlateno());
                    LOGGER.info("车牌：" + payBlackList.getPlateno() + "数量：" + count);
                    if (msg.msgBody.split("@@")[1].equals("add_black")){
                        if(count > 0) {
                            flag = 1;
                        } else {
                            flag = payBlackListMapper.insertSelective(payBlackList);
                        }
                        //新增黑名单记录成功
                        if (flag > 0){
                            //删除消息
                            LOGGER.info("新增黑名单：车牌号：" + payBlackList.getPlateno());
                            queue.deleteMessage(msg.receiptHandle);
                        }
                    }else{
                        if(count > 0) {
                            flag = payBlackListMapper.deleteByPrimaryKey(payBlackList.getPlateno());
                        } else {
                            flag = 1;
                        }
                        //删除黑名单记录成功
                        if (flag > 0){
                            //删除消息
                            LOGGER.info("移除黑名单：车牌号：" + payBlackList.getPlateno());
                            queue.deleteMessage(msg.receiptHandle);
                        }
                    }
                }
            } catch (Exception e) {
                if((e instanceof InterruptedException)||(e instanceof TimeoutException)){
                    LOGGER.error("黑名单CMQ连接异常-------------");
                    e.printStackTrace();
                    MonitorThread  thread = new MonitorThread();
                    thread.cancelBlack();
                    Thread.currentThread().interrupt();
                    LOGGER.error(e.getMessage());
                    futureTask.cancel(true);
                    LOGGER.error("线程状态："+futureTask.isDone());
                    futureTask = null;
                    executorService.shutdownNow();
                    executorService = null;
                } else if(e instanceof CMQServerException) {
                    CMQServerException e1 = (CMQServerException) e;
                    LOGGER.error(e1.getErrorMessage());
                } else if(e instanceof ExecutionException) {
                    LOGGER.error("没有消息：" + e.getMessage());
                } else {
                    LOGGER.error(e.getMessage(),e);
                }
            }
        }
    }
}
