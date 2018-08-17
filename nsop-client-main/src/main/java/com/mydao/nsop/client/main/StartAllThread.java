package com.mydao.nsop.client.main;

import com.mydao.nsop.client.service.*;
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
    private VehicleDriveInBroadcastService vehicleDriveInBroadcastService;

    @Autowired
    private VehicleDriveOutBroadcastService vehicleDriveOutBroadcastService;

    @Autowired
    private CreateSubscriptionAndQueue createSubscriptionAndQueue;

    @Autowired
    private SystemInit systemInit;

    @Autowired
    private VehicleBlackService vehicleBlackService;

    @Autowired
    private VehicleWhiteService vehicleWhiteService;
    @Autowired
    private VehicleDriveInOutExceptionService vehicleDriveInOutExceptionService;

    @PostConstruct
    public void start() {
        //创建订阅和队列
        createSubscriptionAndQueue.createSubQueue();
        //黑白初始化
        systemInit.systemInitBlack();
        //白名单初始化
        systemInit.systemInitWhite();
        //驶入广播
        vehicleDriveInBroadcastService.vehicleDriveIn();
        //驶出广播
        vehicleDriveOutBroadcastService.vehicleDriveOut();
        //黑名单
        vehicleBlackService.addDelBlack();
        //白名单
        vehicleWhiteService.addDelWhite();
        //异常驶入驶出
        vehicleDriveInOutExceptionService.driveExceptCheckInOut();
    }
}
