package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.CMQConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.CMQServerException;
import com.qcloud.cmq.QueueMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZYW
 * @date 2018/5/9
 */
@Service
public class CreateSubscriptionAndQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateSubscriptionAndQueue.class);

    @Autowired
    private CMQConfig config;

    @Autowired
    private TrafficConfig trafficConfig;

    public void createSubQueue(){
        QueueMeta meta2 = new QueueMeta();
        meta2.pollingWaitSeconds = 10;
        meta2.visibilityTimeout = 10;
        meta2.maxMsgSize = 65536;
        meta2.msgRetentionSeconds = 345600;
        Account accountQueue = config.accountQueue();
        Account accountTopic = config.accountTopic();
        //创建车辆驶入通知队列
        String queueName = Constants.VEHICLE_DRIVE_IN_QUEUE + trafficConfig.getClientNum();
        try {
            config.accountQueue().createQueue(queueName,meta2);
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }
        try {
            accountTopic.createSubscribe(Constants.VEHICLE_DRIVE_IN_TOPIC
                    , Constants.VEHICLE_DRIVE_IN_SUB + trafficConfig.getClientNum()
                    ,queueName
                    ,"queue"
                    ,null, null,"BACKOFF_RETRY","SIMPLIFIED");
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }

        //创建车辆驶出通知队列
        queueName = Constants.VEHICLE_DRIVE_OUT_QUEUE + trafficConfig.getClientNum();
        try {
            accountQueue.createQueue(queueName,meta2);
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }
        try {
            accountTopic.createSubscribe(Constants.VEHICLE_DRIVE_OUT_TOPIC
                    , Constants.VEHICLE_DRIVE_OUT_SUB + trafficConfig.getClientNum()
                    ,queueName
                    ,"queue"
                    ,null, null,"BACKOFF_RETRY","SIMPLIFIED");
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }

        //创建车辆白名单队列
        queueName = Constants.VEHICLE_WHITE_QUEUE + trafficConfig.getClientNum();
        try {
            accountQueue.createQueue(queueName,meta2);
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }
        try {
            accountTopic.createSubscribe(Constants.VEHICLE_WHITE_TOPIC
                    , Constants.VEHICLE_WHITE_SUB + trafficConfig.getClientNum()
                    ,queueName
                    ,"queue"
                    ,null, null,"BACKOFF_RETRY","SIMPLIFIED");
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }

        //创建车辆黑名单队列
        queueName = Constants.VEHICLE_BLACK_QUEUE + trafficConfig.getClientNum();
        try {
            accountQueue.createQueue(queueName,meta2);
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }
        try {
            accountTopic.createSubscribe(Constants.VEHICLE_BLACK_TOPIC
                    , Constants.VEHICLE_BLACK_SUB + trafficConfig.getClientNum()
                    ,queueName
                    ,"queue"
                    ,null, null,"BACKOFF_RETRY","SIMPLIFIED");
        } catch (Exception e) {
            if(e instanceof CMQServerException) {
                CMQServerException e1 = (CMQServerException) e;
                LOGGER.error(e1.getErrorMessage());
            }
        }
    }
}
