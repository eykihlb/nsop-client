package com.mydao.nsop.client.main;

import com.mydao.nsop.client.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;

/**
 * @author ZYW
 * @date 2018/5/7
 */
@Component
public class StartAllThread {

    private final static Logger LOGGER = Logger.getLogger(StartAllThread.class);

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

    private final CountDownLatch countDownLatch = new CountDownLatch(2);

    @PostConstruct
    public void start() {
        //创建订阅和队列
        createSubscriptionAndQueue.createSubQueue();
        //黑白初始化
        systemInit.systemInitBlack(countDownLatch);
        //白名单初始化
        systemInit.systemInitWhite(countDownLatch);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            LOGGER.error("CountDownLatch：" + e.getMessage());
        }

        //驶入广播
        vehicleDriveInBroadcastService.vehicleDriveIn();
        //驶出广播
        vehicleDriveOutBroadcastService.vehicleDriveOut();
        //黑名单
        vehicleBlackService.addDelBlack();
        //白名单
        vehicleWhiteService.addDelWhite();
        //异常驶入驶出
        //vehicleDriveInOutExceptionService.driveExceptCheckInOut();
    }
}
