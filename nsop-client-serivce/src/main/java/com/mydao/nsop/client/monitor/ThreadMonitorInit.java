package com.mydao.nsop.client.monitor;

import com.mydao.nsop.client.util.ThreadPoolFtp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ThreadMonitorInit implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadMonitorInit.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
                if(event.getApplicationContext().getParent() == null){
                    LOGGER.info("初始化监控线程^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                    Thread thread = new Thread(new MonitorThread());
                    thread.start();
                    //初始化线程池
                    LOGGER.info("初始化Ftp线程池^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                    ThreadPoolFtp.ftpThreadPool();
                }
    }
}
