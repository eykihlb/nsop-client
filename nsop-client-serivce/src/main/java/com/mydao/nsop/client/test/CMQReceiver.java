package com.mydao.nsop.client.test;

import com.mydao.nsop.client.common.Constants;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2018/5/9
 */
public class CMQReceiver {
    public static void main(String[] args) {
        String secretId="AKIDe8OPClQB2f3D9qtSO7bqqb6MOzMVv7ap";
        String secretKey="A6oMgngOs8AxWqwmNmvoRpyrUryqTqKd";
        String endpoint2 = "https://cmq-queue-bj.api.qcloud.com";
        String path = "/v2/index.php";
        String method = "POST";

        Account account2 = new Account(endpoint2,secretId, secretKey);


        try {
            Queue queue = account2.getQueue(Constants.VEHICLE_BLACK_QUEUE + "1");
            List<Message> messages = queue.batchReceiveMessage(10, 20);
            for(Message message : messages) {
                System.out.println(message.msgId);
                System.out.println(message.msgBody);
            }
            queue.batchDeleteMessage(messages.stream().map(item -> item.receiptHandle).collect(Collectors.toList()));

            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            /*Queue queue2 = account2.getQueue(Constants.VEHICLE_BLACK_QUEUE + "1");
            List<Message> messages2 = queue2.batchReceiveMessage(10,20);
            for(Message message : messages2) {
                System.out.println(message.msgTag);
            }
            System.out.println("消息2：" + Joiner.on("--").join(messages2.stream().map(item -> item.msgBody).collect(Collectors.toList())));

            queue2.batchDeleteMessage(messages2.stream().map(item -> item.receiptHandle).collect(Collectors.toList()));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
