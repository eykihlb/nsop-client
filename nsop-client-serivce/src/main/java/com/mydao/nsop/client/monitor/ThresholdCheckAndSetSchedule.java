package com.mydao.nsop.client.monitor;

import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.service.SystemInit;
import com.mydao.nsop.client.util.ClientBeanHolder;
import com.mydao.nsop.client.util.ConfigFileUtil;
import com.mydao.nsop.client.util.DateUtil;
import com.mydao.nsop.client.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: nsop-client
 * @description:拉取黑白名单更新阀值检查设定
 * @author: miaosy
 * @create:
 */
@Component
public class ThresholdCheckAndSetSchedule {
    private static final Logger logger = LoggerFactory.getLogger(ThresholdCheckAndSetSchedule.class);
    @Autowired
    private SystemInit systemInit;



    @Scheduled(cron="0 0/2 * * * ?")
    public void stateCheckSetSchedule(){
        ConfigFileUtil  config = new ConfigFileUtil();
        //读文件
       String time = config.readTimeFromFile();
       String nowTime = DateUtil.dateToShortCode(new Date());
               boolean sign = false;
               TrafficConfig trafficConfig = ClientBeanHolder.getBean("trafficConfig", TrafficConfig.class);
               MonitorThread  th = new MonitorThread();
               if(!th.isFinishFlag()){
                   logger.info("线上黑白名单拉取未完成，请等待更新完成！@#￥%……&*（）—_—++");
                   return;
               }
               int times =0;
               while(times<3){
                   logger.info("定时检查网络任务,第 -"+(times+1)+"-检查！！！");
                   sign = IpUtil.pingServer(trafficConfig.getQqDns(),10);
                   if(sign){
                       //写文件
                       if(!time.equals(nowTime)){
                           logger.info("当前阈值为："+time+" 网络连接正常更新阈值日期："+nowTime);
                           config.writeTimeFile();
                       }else{
                           logger.info("当前配置文件阈值为："+time+" 与当前时间相同,不更新文件阀值。");
                       }
                       break;

                   }else {
                       times++;
                   }
               }
               if(!sign){
                   logger.error("当前阈值为"+time+"网络连接异常不更新阈值日期。");
               }

    }





}
