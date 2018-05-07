package com.mydao.nsop.client.main;

import com.mydao.nsop.client.service.VehicleDriveInService;
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
    private VehicleDriveInService vehicleDriveInService;

    @PostConstruct
    public void start() {
        vehicleDriveInService.test();
        vehicleDriveInService.test2();
        vehicleDriveInService.test3();
    }
}
