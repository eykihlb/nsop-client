package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.CMQConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayWhiteListMapper;
import com.mydao.nsop.client.domain.entity.PayWhiteList;
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
public class VehicleWhiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleWhiteService.class);

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static void setExecutorService(ExecutorService executorService) {
        VehicleWhiteService.executorService = executorService;
    }

    @Autowired
    private PayWhiteListMapper payWhiteListMapper;

    @Autowired
    private CMQConfig config;

    @Autowired
    private TrafficConfig trafficConfig;

    private Gson gson = new Gson();

    @Async
    public void addDelWhite() {
        PayWhiteList payWhiteList = new PayWhiteList();
        int flag = 0;
        while(!Thread.interrupted()) {
            LOGGER.info("白名单线程，时间：" + DateTime.now().toString("YYYY-MM-dd HH:mm:ss"));
            Future<List<Message>> futureTask = null ;
            try {
                Account accountQueue = config.accountQueue();
                Queue queue = accountQueue.getQueue(Constants.VEHICLE_WHITE_QUEUE + trafficConfig.getClientNum());
                futureTask = executorService.submit(new WhiteCmqCall(accountQueue,trafficConfig.getClientNum()));
                List<Message> messageList = futureTask.get(20, TimeUnit.SECONDS);
                messageList.sort(Comparator.comparing((Message m) -> Integer.parseInt(m.msgBody.split("@@")[0] )) );
                for (Message msg : messageList) {
                    LOGGER.info("接收到的白名单："+msg.msgBody);
                    String message = msg.msgBody.split("@@")[2];
                    if(StringUtils.isEmpty(message)) {
                        LOGGER.warn("接收到的主题消息为空！");
                        queue.deleteMessage(msg.receiptHandle);
                        continue;
                    }
                    Map<String,Object> map = gson.fromJson(new String(msg.msgBody.split("@@")[2]),Map.class);
                    payWhiteList.setBand(map.get("band").toString());
                    payWhiteList.setBodycolor(map.get("bodycolor").toString());
                    payWhiteList.setPlatecolor(map.get("platecolor").toString());
                    payWhiteList.setPlateno(map.get("plateno").toString());
                    payWhiteList.setSubBand(map.get("subBand").toString());
                    payWhiteList.setUptime(new Date());
                    payWhiteList.setVehclass(Objects.toString(map.get("vehclass"),"0"));
                    int count = payWhiteListMapper.selectByPlateNo(payWhiteList.getPlateno());
                    if (msg.msgBody.split("@@")[1].equals("add_white")){
                        if (count > 0) {
                            flag = 1;
                        }else{
                            flag = payWhiteListMapper.insertSelective(payWhiteList);
                        }
                        //新增白名单记录成功
                        if (flag > 0){
                            //删除消息
                            LOGGER.info("新增白名单：车牌号："+payWhiteList.getPlateno());
                            queue.deleteMessage(msg.receiptHandle);
                        }
                    }else{
                        if (count > 0) {
                            flag = payWhiteListMapper.deleteByPrimaryKey(payWhiteList.getPlateno());
                        }else{
                            flag = 1;
                        }
                        //删除白名单记录成功
                        if (flag > 0){
                            //删除消息
                            LOGGER.info("移除白名单：车牌号："+payWhiteList.getPlateno());
                            queue.deleteMessage(msg.receiptHandle);
                        }
                    }

                }
            } catch (Exception e) {
                if((e instanceof InterruptedException)||(e instanceof TimeoutException)){
                    LOGGER.error("CMQ连接异常-------------");
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    MonitorThread thread = new MonitorThread();
                    thread.cancelWhite();
                    Thread.currentThread().interrupt();
                    futureTask.cancel(true);
                    LOGGER.error("线程状态："+futureTask.isDone());
                    futureTask = null;
                    executorService.shutdownNow();
                    LOGGER.error("线程池状态："+executorService.isShutdown());
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
