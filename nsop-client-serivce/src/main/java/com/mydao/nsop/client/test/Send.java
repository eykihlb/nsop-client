package com.mydao.nsop.client.test;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String QUEUE_NAME = "test_queue";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 消息内容

        for(int i = 1; i <= 100; i++) {
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
            AMQP.BasicProperties bp = null;
            if((i%2)==0) {
                bp = builder.contentType("aaaa").build();
            } else {
                bp = builder.contentType("bbbb").build();
            }

            String message = "Hello World!" + i;
            channel.basicPublish("", QUEUE_NAME, bp, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}