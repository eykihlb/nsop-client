package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.CMQConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayIssuedRecMapper;
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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author ZYW
 * @date 2018/5/8
 */
@Service
public class VehicleDriveOutBroadcastService {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void setExecutorService(ExecutorService executorService) {
        VehicleDriveOutBroadcastService.executorService = executorService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDriveOutBroadcastService.class);

    @Autowired
    private CMQConfig config;

    @Autowired
    private TrafficConfig trafficConfig;

    @Autowired
    private PayIssuedRecMapper payIssuedRecMapper;

    private Gson gson = new Gson();

    @Async
    public void vehicleDriveOut() {
        while(!Thread.interrupted()) {
            LOGGER.info("车辆驶出线程，时间：" + DateTime.now().toString("YYYY-MM-dd HH:mm:ss"));
            Future<Message> futureTask = null ;
            try {
                Account accountQueue = config.accountQueue();
                Queue queue = accountQueue.getQueue(Constants.VEHICLE_DRIVE_OUT_QUEUE + trafficConfig.getClientNum());
                futureTask = executorService.submit(new DirveOutCmqCall(accountQueue,trafficConfig.getClientNum()));
                Message message =  futureTask.get(20, TimeUnit.SECONDS);
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
                paramMap.put("plateNo",message.msgBody);
                if(payIssuedRecMapper.selectById(paramMap) != null){
                    payIssuedRecMapper.updateByPlateNo(map);
                    LOGGER.info("车牌号为："+message.msgBody+"的记录更新为驶出！");
                    queue.deleteMessage(message.receiptHandle);
                }else{
                    LOGGER.warn("未找到车牌号为："+message.msgBody+"的驶入记录！");
                    queue.deleteMessage(message.receiptHandle);
                }

            } catch (Exception e) {
                if((e instanceof InterruptedException)||(e instanceof TimeoutException)){
                    LOGGER.error("驶出CMQ连接异常-------------");
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    futureTask.cancel(true);
                    LOGGER.error("线程状态："+futureTask.isDone());
                    futureTask = null;
                    executorService.shutdownNow();
                    MonitorThread thread = new MonitorThread();
                    thread.cancelOut();
                    Thread.currentThread().interrupt();
                    LOGGER.error("线程池状态："+executorService.isShutdown());
                    executorService = null;
                } else if(e instanceof CMQServerException) {
                    CMQServerException e1 = (CMQServerException) e;
                    LOGGER.error(e1.getErrorMessage());
                }  else if(e instanceof ExecutionException) {
                    LOGGER.error("没有消息：" + e.getMessage());
                } else {
                    LOGGER.error(e.getMessage(),e);
                }
            }
        }
    }
}
