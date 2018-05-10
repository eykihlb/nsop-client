package com.mydao.nsop.client.test;

import com.qcloud.cmq.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/5/8
 */
public class CMQMain {
    public static void main(String[] args) {
        String secretId="AKIDe8OPClQB2f3D9qtSO7bqqb6MOzMVv7ap";
        String secretKey="A6oMgngOs8AxWqwmNmvoRpyrUryqTqKd";
        String endpoint = "https://cmq-queue-bj.api.qcloud.com";
        String path = "/v2/index.php";
        String method = "POST";

        try
        {
            Account account = new Account(endpoint,secretId, secretKey);

            /*account.deleteQueue("queue-test10");
            System.out.println("---------------create queue ...---------------");
            QueueMeta meta = new QueueMeta();
            meta.pollingWaitSeconds = 10;
            meta.visibilityTimeout = 10;
            meta.maxMsgSize = 65536;
            meta.msgRetentionSeconds = 345600;
            account.createQueue("queue-test10",meta);
            System.out.println("queue-test10 created");
            account.createQueue("queue-test11",meta);
            System.out.println("queue-test11 created");
            account.createQueue("queue-test12",meta);
            System.out.println("queue-test12 created");

            System.out.println("---------------list queue ...---------------");
            ArrayList<String> vtQueue = new ArrayList<String>();
            int totalCount = account.listQueue("",-1,-1,vtQueue);
            System.out.println("totalCount:" + totalCount);
            for(int i=0; i < vtQueue.size(); i++) {
                System.out.println("queueName:" + vtQueue.get(i));
            }

            System.out.println("---------------delete queue ...---------------");
            account.deleteQueue("queue-test11");
            System.out.println("queue-test11 deleted");
            account.deleteQueue("queue-test12");
            System.out.println("queue-test12 deleted");*/

            System.out.println("--------------- queue[queue-test10] ---------------");
            Queue queue = account.getQueue("nsop-cloud-mq");

           /* System.out.println("---------------set queue attributes ...---------------");
            QueueMeta meta1 = new QueueMeta();
            meta1.pollingWaitSeconds = 20;
            queue.setQueueAttributes(meta1);
            System.out.println("pollingWaitSeconds=20 set");

            System.out.println("---------------get queue attributes ...---------------");
            QueueMeta meta2 = queue.getQueueAttributes();
            System.out.println("maxMsgHeapNum:" + meta2.maxMsgHeapNum);
            System.out.println("pollingWaitSeconds:" + meta2.pollingWaitSeconds);
            System.out.println("visibilityTimeout:" + meta2.visibilityTimeout);
            System.out.println("maxMsgSize:" + meta2.maxMsgSize);
            System.out.println("createTime:" + meta2.createTime);
            System.out.println("lastModifyTime:" + meta2.lastModifyTime);
            System.out.println("activeMsgNum:" + meta2.activeMsgNum);
            System.out.println("inactiveMsgNum:" + meta2.inactiveMsgNum);*/

            System.out.println("---------------send message ...---------------");
            String msgId = queue.sendMessage("hello world,this is cmq sdk for java");
            System.out.println("[hello world,this is cmq sdk for java] sent");

            System.out.println("---------------recv message ...---------------");
            Message msg = queue.receiveMessage(10);

            System.out.println("msgId:" + msg.msgId);
            System.out.println("msgBody:" + msg.msgBody);
            System.out.println("receiptHandle:" + msg.receiptHandle);
            System.out.println("enqueueTime:" + msg.enqueueTime);
            System.out.println("nextVisibleTime:" + msg.nextVisibleTime);
            System.out.println("firstDequeueTime:" + msg.firstDequeueTime);
            System.out.println("dequeueCount:" + msg.dequeueCount);

            System.out.println("---------------delete message ...---------------");
            queue.deleteMessage(msg.receiptHandle);
            System.out.println("receiptHandle:" + msg.receiptHandle +" deleted");

            System.out.println("---------------batch send message ...---------------");
            ArrayList<String> vtMsgBody = new ArrayList<String>();
            String msgBody = "hello world,this is cmq sdk for java 1";
            vtMsgBody.add(msgBody);
            msgBody = "hello world,this is cmq sdk for java 2";
            vtMsgBody.add(msgBody);
            msgBody = "hello world,this is cmq sdk for java 3";
            vtMsgBody.add(msgBody);
            List<String> vtMsgId = queue.batchSendMessage(vtMsgBody);
            for(int i=0;i<vtMsgBody.size();i++) {
                System.out.println("[" + vtMsgBody.get(i) + "] sent");
            }
            for(int i=0;i<vtMsgId.size();i++) {
                System.out.println("msgId:" + vtMsgId.get(i));
            }

            ArrayList<String> vtReceiptHandle = new ArrayList<String>();
            System.out.println("---------------batch recv message ...---------------");
            List<Message> msgList = queue.batchReceiveMessage(10,10);
            System.out.println("recv msg count:" + msgList.size());
            for(int i=0;i<msgList.size();i++) {
                Message msg1 = msgList.get(i);
                System.out.println("msgId:" + msg1.msgId);
                System.out.println("msgBody:" + msg1.msgBody);
                System.out.println("receiptHandle:" + msg1.receiptHandle);
                System.out.println("enqueueTime:" + msg1.enqueueTime);
                System.out.println("nextVisibleTime:" + msg1.nextVisibleTime);
                System.out.println("firstDequeueTime:" + msg1.firstDequeueTime);
                System.out.println("dequeueCount:" + msg1.dequeueCount);

                vtReceiptHandle.add(msg1.receiptHandle);
            }

            queue.batchDeleteMessage(vtReceiptHandle);
            System.out.println("---------------batch delete message ...---------------");
            for(int i=0;i<vtReceiptHandle.size();i++) {
                System.out.println("receiptHandle:" + vtReceiptHandle.get(i) + " deleted");
            }
        }
        catch(CMQServerException e1){
            System.out.println("Server Exception, " + e1.toString());
        } catch(CMQClientException e2){
            System.out.println("Client Exception, " + e2.toString());
        }
        catch (Exception e) {
            System.out.println("error..." + e.toString());
        }
    }
}
