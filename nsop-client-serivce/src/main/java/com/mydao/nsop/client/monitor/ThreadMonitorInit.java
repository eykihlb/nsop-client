package com.mydao.nsop.client.monitor;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ThreadMonitorInit {

    @PostConstruct
    private void init(){
        Thread thread = new Thread(new MonitorThread());
        thread.start();

    }
}
