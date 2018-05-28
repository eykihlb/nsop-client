package com.mydao.nsop.client.test;

import com.mydao.nsop.client.common.Constants;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.Date;

public class Send {

    private final static String QUEUE_NAME = Constants.GET_BWLIST_QUEUE;

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列
        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 消息内容
        /*for(int i = 1; i <= 2; i++) {
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
            AMQP.BasicProperties bp = null;
            if((i%2)==0) {
                bp = builder.contentType("aaaa").build();
            } else {
                bp = builder.contentType("bbbb").build();
            }
*/
            String message = "black";
            String i = "1";
            String eq = "{'laneNo':'65000115E01','passTime':'445555555','plateNo':'京A12345-1','src':'00','feature':'00_00_00','cameraId':'000001','passSeq':'2000','clientId':'000001','clientSeq':'"+i+"'}";
            String eeq = "{'laneNo':'65000115B03','passTime':'445555555','plateNo':'京A00000-1','src':'00','feature':'00_00_00','cameraId':'000001','passSeq':'2000','status':'00','clientId':'000001','clientSeq':'"+i+"'}";
            String edq = "{'laneNo':'65000115B03','passTime':'445555555','plateNo':'京A00000-1','createTime':'"+new Date().getTime()+"'}";
            String prq = "{'laneNo':'65000115B03','passTime':'445555555','plateNo':'京A00000-1','status':'00','feature':'00_00_00','cameraId':'000001','passSeq':'"+i+"','passType':'00'}";
            String exq = "{'laneNo':'65000115B03','passTime':'445555555','plateNo':'京A00000-1','src':'00','entryId':'65000207E01_126148652f91','feature':'00_00_00','cameraId':'000001','passSeq':'2000','clientId':'000001','clientSeq':'1','entryClientId':'000001','entryClientSeq':'1','vehClass':'13','detectWeight':'12.6','fareWeight':'12.51','detectAxles':'4','distance':'20.2','payFare':'"+i+"'}";
            String aa = "{'laneNo':'65000115B03','passTime':'445555555','plateNo':'京A00000-1','src':'00','feature':'00_00_00','cameraId':'000001','passSeq':'2000','status':'00','entryId':'a00001_126148652f91','clientId':'000001','clientSeq':'"+i+"'}";

            channel.basicPublish("", Constants.ENTRY_QUEUE, null, eq.getBytes());
            channel.basicPublish("", Constants.ENTRY_EX_QUEUE, null, eeq.getBytes());
            channel.basicPublish("", Constants.ENTRY_DENY_QUEUE, null, edq.getBytes());
            channel.basicPublish("", Constants.PASS_REJECT_QUEUE, null, prq.getBytes());
            channel.basicPublish("", Constants.EXIT_QUEUE, null, exq.getBytes());
            channel.basicPublish("", Constants.EXIT_EX_QUEUE, null, aa.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        //}

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}