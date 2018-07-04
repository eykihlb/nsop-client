package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.CMQConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayIssuedRecMapper;
import com.mydao.nsop.client.domain.entity.PayIssuedRec;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author ZYW
 * @date 2018/5/8
 */
@Service
public class VehicleDriveInBroadcastService {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void setExecutorService(ExecutorService executorService) {
        VehicleDriveInBroadcastService.executorService = executorService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDriveInBroadcastService.class);

    @Autowired
    private CMQConfig config;

    @Autowired
    private TrafficConfig trafficConfig;

    @Autowired
    private PayIssuedRecMapper payIssuedRecMapper;

    private Gson gson = new Gson();

    @Async
    public void vehicleDriveIn() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        while(!Thread.interrupted()) {
            LOGGER.info("车辆驶入线程，时间：" + DateTime.now().toString("YYYY-MM-dd HH:mm:ss"));
            Future<Message> futureTask = null ;
            try {
                Account accountQueue = config.accountQueue();
                Queue queue = accountQueue.getQueue(Constants.VEHICLE_DRIVE_IN_QUEUE + trafficConfig.getClientNum());
                PayIssuedRec pir = new PayIssuedRec();
                //PayIssuedRec payi = new PayIssuedRec();
                //Map<String,Object> paramMap = new HashMap<>();
                futureTask = executorService.submit(new DriveInCmqCall(accountQueue,trafficConfig.getClientNum()));
                Message message = futureTask.get(20, TimeUnit.SECONDS);
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
                /*paramMap.put("status","1");
                paramMap.put("plateno",pir.getPlateno());*/
                int count = payIssuedRecMapper.selectCountByPlateNoAndStatus(pir.getPlateno(), "1");
                if (count == 0){
                    int insert = payIssuedRecMapper.insertSelective(pir);
                    if(insert > 0) {
                        LOGGER.info("写入车牌号为："+pir.getPlateno()+"的驶入记录！");
                        queue.deleteMessage(message.receiptHandle);
                    }
                } else {
                    LOGGER.warn("车辆驶入车牌号重复，车牌号：" + pir.getPlateno());
                    queue.deleteMessage(message.receiptHandle);
                }
            } catch (Exception e) {
                if((e instanceof InterruptedException)||(e instanceof TimeoutException)){
                    LOGGER.error("驶入CMQ连接异常-------------");
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    futureTask.cancel(true);
                    LOGGER.error("线程状态："+futureTask.isDone());
                    futureTask = null;
                    MonitorThread thread = new MonitorThread();
                    thread.cancelIn();
                    Thread.currentThread().interrupt();
                    executorService.shutdownNow();
                    executorService = null;
                } else if(e instanceof CMQServerException) {
                    CMQServerException e1 = (CMQServerException) e;
                    LOGGER.error(e1.getErrorMessage());
                } else {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }
}
