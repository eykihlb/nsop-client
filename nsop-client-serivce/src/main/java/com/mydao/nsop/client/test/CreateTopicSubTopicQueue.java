package com.mydao.nsop.client.test;

import com.google.common.collect.Lists;
import com.mydao.nsop.client.common.Constants;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.QueueMeta;

/**
 * @author Administrator
 * @date 2018/5/9
 */
public class CreateTopicSubTopicQueue {

    public static final String TOPIC_NAME = "test-topic";
    public static final String SUB_TOPIC1_NAME = "sub-test-topic1";
    public static final String SUB_TOPIC2_NAME = "sub-test-topic2";

    public static final String SUB1_QUEUE_NAME = "sub1-queue-name";
    public static final String SUB1_KEY = "sub1-key";

    public static final String SUB2_QUEUE_NAME = "sub2-queue-name";
    public static final String SUB2_KEY = "sub2-key";

    public static void main(String[] args) {
        String secretId="AKIDe8OPClQB2f3D9qtSO7bqqb6MOzMVv7ap";
        String secretKey="A6oMgngOs8AxWqwmNmvoRpyrUryqTqKd";
        String endpoint1 = "https://cmq-topic-bj.api.qcloud.com";
        String endpoint2 = "https://cmq-queue-bj.api.qcloud.com";
        String path = "/v2/index.php";
        String method = "POST";

        Account account1 = new Account(endpoint1,secretId, secretKey);
        Account account2 = new Account(endpoint2,secretId, secretKey);

        try {
            account1.createTopic(Constants.VEHICLE_DRIVE_IN_TOPIC,65536);
            account1.createTopic(Constants.VEHICLE_WHITE_TOPIC,65536);
            account1.createTopic(Constants.VEHICLE_BLACK_TOPIC,65536);
            /*account2.deleteQueue(SUB1_QUEUE_NAME);
            account2.deleteQueue(SUB2_QUEUE_NAME);
            account1.deleteSubscribe(TOPIC_NAME,SUB_TOPIC1_NAME);
            account1.deleteSubscribe(TOPIC_NAME,SUB_TOPIC2_NAME);
            account1.deleteTopic(CreateTopicSubTopicQueue.TOPIC_NAME);*//*
            //创建主题
            account1.createTopic(CreateTopicSubTopicQueue.TOPIC_NAME,65536,2);


            //创建订阅 sub1
            account1.createSubscribe(TOPIC_NAME,SUB_TOPIC1_NAME,SUB1_QUEUE_NAME,"queue",null, Lists.newArrayList(SUB1_KEY),"BACKOFF_RETRY","SIMPLIFIED");
            QueueMeta meta1 = new QueueMeta();
            meta1.pollingWaitSeconds = 10;
            meta1.visibilityTimeout = 10;
            meta1.maxMsgSize = 65536;
            meta1.msgRetentionSeconds = 345600;
            account2.createQueue(SUB1_QUEUE_NAME,meta1);


            //创建订阅 sub2
            account1.createSubscribe(TOPIC_NAME,SUB_TOPIC2_NAME,SUB2_QUEUE_NAME,"queue",null, Lists.newArrayList(SUB2_KEY),"BACKOFF_RETRY","SIMPLIFIED");
            QueueMeta meta2 = new QueueMeta();
            meta2.pollingWaitSeconds = 10;
            meta2.visibilityTimeout = 10;
            meta2.maxMsgSize = 65536;
            meta2.msgRetentionSeconds = 345600;
            account2.createQueue(SUB2_QUEUE_NAME,meta2);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
