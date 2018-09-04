package com.mydao.nsop.client.monitor;

import com.mydao.nsop.client.config.InterFaceConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.service.VehicleBlackService;
import com.mydao.nsop.client.service.VehicleDriveInBroadcastService;
import com.mydao.nsop.client.service.VehicleDriveOutBroadcastService;
import com.mydao.nsop.client.service.VehicleWhiteService;
import com.mydao.nsop.client.util.ClientBeanHolder;
import com.mydao.nsop.client.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class MonitorThread implements  Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MonitorThread.class);
    private static volatile boolean blackFlag = false;
    private static volatile boolean whiteFlag = false;
    private static volatile boolean inFlag = false;
    private static volatile boolean outFlag = false;
    @Override
    public void run() {
            while(!Thread.interrupted()){
                logger.info("监控线程睡眠60秒");
                try {
                    TimeUnit.SECONDS.sleep(60);
                    //心跳
                    OAuth2RestTemplate oAuthRestTemplate = ClientBeanHolder.getBean("oAuthRestTemplate",OAuth2RestTemplate.class);
                    TrafficConfig trafficConfig = ClientBeanHolder.getBean("trafficConfig",TrafficConfig.class);
                    InterFaceConfig interFaceConfig = ClientBeanHolder.getBean("interFaceConfig",InterFaceConfig.class);
                    String heartUrl = trafficConfig.getUrl()+interFaceConfig.getHeartbeat();
                    MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
                    requestEntity.add("tollsiteNo", trafficConfig.getClientNum());
                    ResponseEntity<Map> blackEntity = oAuthRestTemplate.postForEntity(heartUrl,requestEntity,Map.class);

                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                boolean sign = false;
                if(blackFlag||whiteFlag||inFlag||outFlag){
                    TrafficConfig trafficConfig = ClientBeanHolder.getBean("trafficConfig", TrafficConfig.class);
                    sign = IpUtil.pingServer(trafficConfig.getQqDns(),10);
                    if(!sign){
                        logger.error("网络连接异常，请检查网络连接！！！");
                    }
                }

                if(blackFlag){
                    if(sign){
                        logger.info("网络已连通，重启黑名单线程中####");
                        VehicleBlackService.setExecutorService(Executors.newSingleThreadExecutor());
                        VehicleBlackService vehicleBlackService = ClientBeanHolder.getBean("vehicleBlackService", VehicleBlackService.class);
                        vehicleBlackService.addDelBlack();
                        blackFlag = false;
                    }
                }
                if(whiteFlag){
                    if(sign){
                        logger.info("网络已连通，重启白名单线程中****");
                        VehicleWhiteService.setExecutorService(Executors.newSingleThreadExecutor());
                        VehicleWhiteService vehicleWhiteService = ClientBeanHolder.getBean("vehicleWhiteService", VehicleWhiteService.class);
                        vehicleWhiteService.addDelWhite();
                        whiteFlag = false;
                    }
                }
                if(inFlag){
                    if(sign){
                        logger.info("网络连通，重启驶入线程中....");
                        VehicleDriveInBroadcastService.setExecutorService(Executors.newSingleThreadExecutor());
                        VehicleDriveInBroadcastService vehicleDriveInBroadcastService = ClientBeanHolder.getBean("vehicleDriveInBroadcastService", VehicleDriveInBroadcastService.class);
                        vehicleDriveInBroadcastService.vehicleDriveIn();
                        inFlag = false;
                    }
                }
                if(outFlag){
                    if(sign){
                        logger.info("网络连通，重启驶出线程@--");
                        VehicleDriveOutBroadcastService.setExecutorService(Executors.newSingleThreadExecutor());
                        VehicleDriveOutBroadcastService vehicleDriveOutBroadcastService = ClientBeanHolder.getBean("vehicleDriveOutBroadcastService", VehicleDriveOutBroadcastService.class);
                        vehicleDriveOutBroadcastService.vehicleDriveOut();
                        outFlag = false;
                    }
                }
            }
    }

    public  void cancelBlack(){
        blackFlag = true;
    }
    public  void cancelWhite(){
        whiteFlag = true;
    }
    public  void cancelIn(){
        inFlag = true;
    }
    public  void cancelOut(){
        outFlag = true;
    }
}
