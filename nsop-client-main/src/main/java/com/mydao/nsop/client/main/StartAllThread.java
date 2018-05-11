package com.mydao.nsop.client.main;

import com.mydao.nsop.client.service.CreateSubscriptionAndQueue;
import com.mydao.nsop.client.service.VehicleDriveInBroadcastService;
import com.mydao.nsop.client.service.VehicleDriveInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ZYW
 * @date 2018/5/7
 */
@Component
public class StartAllThread {

    @Autowired
    private VehicleDriveInOutService vehicleDriveInService;

    @Autowired
    private VehicleDriveInBroadcastService vehicleDriveInBroadcastService;

    @Autowired
    private CreateSubscriptionAndQueue createSubscriptionAndQueue;

    @PostConstruct
    public void start() {
        //创建订阅和队列
        createSubscriptionAndQueue.createSubQueue();

//        vehicleDriveInService.test();
        vehicleDriveInService.test2();
//        vehicleDriveInService.test3();

        vehicleDriveInBroadcastService.vehicleDriveIn();
    }
}
