package com.mydao.nsop.client.main;

import com.mydao.nsop.client.service.*;
import com.rabbitmq.client.Channel;
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

    @Autowired
    private VehicleWhiteService vehicleWhiteService;

    @Autowired
    private VehicleBlackService vehicleBlackService;

    @Autowired
    private SystemInit systemInit;

    @PostConstruct
    public void start() {
        //创建订阅和队列
        createSubscriptionAndQueue.createSubQueue();
        //初始化拉取全量黑白名单数据
        systemInit.systemInit();
        systemInit.aaa();
//        vehicleDriveInService.test();
//        vehicleDriveInService.test2();
//        vehicleDriveInService.test3();
        vehicleBlackService.addDelBlack();
        //vehicleWhiteService.addDelWhite();
        vehicleDriveInBroadcastService.vehicleDriveIn();
    }
}
