package com.mydao.nsop.client.test;

import com.google.common.collect.Lists;
import com.mydao.nsop.client.common.Constants;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.Topic;

/**
 * @author Administrator
 * @date 2018/5/9
 */
public class PushMessage {
    public static void main(String[] args) {
        String secretId="AKIDe8OPClQB2f3D9qtSO7bqqb6MOzMVv7ap";
        String secretKey="A6oMgngOs8AxWqwmNmvoRpyrUryqTqKd";
        String endpoint1 = "https://cmq-topic-bj.api.qcloud.com";
        String path = "/v2/index.php";
        String method = "POST";

        Account account1 = new Account(endpoint1,secretId, secretKey);

        try {
            Topic topic = account1.getTopic(Constants.VEHICLE_BLACK_TOPIC);
            for(int i = 1; i <= 10; i++) {
                String message = "Hello World.==" + i;
                if((i % 2) == 0) {
                    topic.publishMessage(message, Lists.newArrayList(Constants.ADD_BLACK_KEY),"");
                    //topic.publishMessage(message,CreateTopicSubTopicQueue.SUB1_KEY);
                } else {
                    topic.publishMessage(message, Lists.newArrayList(Constants.DEL_BLACK_KEY),"");
                    //topic.publishMessage(message,CreateTopicSubTopicQueue.SUB2_KEY);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
