package com.mydao.nsop.client.config;

import com.mydao.nsop.client.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author ZYW
 * @date 2018/5/7
 */
@Configuration
public class RabbitConfig {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 定制化amqp模版      可根据需要定制多个
     *
     *
     * 此处为模版类定义 Jackson消息转换器
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
     * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
     *
     * @return the amqp template
     */
//    @Primary
    @Bean
    public AmqpTemplate amqpTemplate() {
        Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
//          使用jackson 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
//        开启returncallback     yml 需要 配置    publisher-returns: true
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationIdString();
            log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });
        //        消息确认  yml 需要配置   publisher-returns: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.debug("消息发送到exchange失败,原因: {}", cause);
            }
        });
        return rabbitTemplate;
    }

    /**
     * 声明车辆驶入驶出direct交换机 支持持久化.
     *
     * @return the exchange
     */
    @Bean("driveInOutExchange")
    public Exchange driveInOutExchange() {
        return ExchangeBuilder.directExchange(Constants.TOPIC_TSX_JOURNEY).durable(true).build();
    }

    /**
     * 声明一个车辆驶入队列 支持持久化.
     *
     * @return the queue
     */
    @Bean("entryQueue")
    public Queue entryQueue() {
        return QueueBuilder.durable(Constants.ENTRY_QUEUE).build();
    }

    /**
     * 通过车辆驶入绑定键 将指定车辆驶入队列绑定到一个指定的交换机 .
     *
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding entryBinding(@Qualifier("entryQueue") Queue queue, @Qualifier("driveInOutExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.ENTRY_KEY).noargs();
    }



    @Bean("entryExQueue")
    public Queue entryExQueue() {
        return QueueBuilder.durable(Constants.ENTRY_EX_QUEUE).build();
    }
    @Bean
    public Binding entryExBinding(@Qualifier("entryExQueue") Queue queue, @Qualifier("driveInOutExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.ENTRY_EX_KEY).noargs();
    }


    @Bean("entryDenyQueue")
    public Queue entryDenyQueue() {
        return QueueBuilder.durable(Constants.ENTRY_DENY_QUEUE).build();
    }
    @Bean
    public Binding entryDenyBinding(@Qualifier("entryDenyQueue") Queue queue, @Qualifier("driveInOutExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.ENTRY_DENY_KEY).noargs();
    }


    @Bean("passRejectQueue")
    public Queue passRejectQueue() {
        return QueueBuilder.durable(Constants.PASS_REJECT_QUEUE).build();
    }
    @Bean
    public Binding passRejectBinding(@Qualifier("passRejectQueue") Queue queue, @Qualifier("driveInOutExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.PASS_REJECT_KEY).noargs();
    }

    @Bean("exitQueue")
    public Queue exitQueue() {
        return QueueBuilder.durable(Constants.EXIT_QUEUE).build();
    }
    @Bean
    public Binding exitBinding(@Qualifier("exitQueue") Queue queue, @Qualifier("driveInOutExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.EXIT_KEY).noargs();
    }


    @Bean("exitExQueue")
    public Queue exitExQueue() {
        return QueueBuilder.durable(Constants.EXIT_EX_QUEUE).build();
    }
    @Bean
    public Binding exitExBinding(@Qualifier("exitExQueue") Queue queue, @Qualifier("driveInOutExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.EXIT_EX_KEY).noargs();
    }

    /*---------------------------------- 车辆黑名单 -----------------------------------*/

    @Bean("blackExchange")
    public Exchange blackExchange() {
        return ExchangeBuilder.directExchange(Constants.TOPIC_TSX_BLACKVEH).durable(true).build();
    }

    @Bean("addBlackQueue")
    public Queue addBlackQueue() {
        return QueueBuilder.durable(Constants.ADD_BLACK_QUEUE).build();
    }
    @Bean
    public Binding addBlackBinding(@Qualifier("addBlackQueue") Queue queue, @Qualifier("blackExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.ADD_BLACK_KEY).noargs();
    }

    @Bean("delBlackQueue")
    public Queue delBlackQueue() {
        return QueueBuilder.durable(Constants.DEL_BLACK_QUEUE).build();
    }
    @Bean
    public Binding delBlackBinding(@Qualifier("delBlackQueue") Queue queue, @Qualifier("blackExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.DEL_BLACK_KEY).noargs();
    }


    /*---------------------------------- 车辆白名单 -----------------------------------*/

    @Bean("whiteExchange")
    public Exchange whiteExchange() {
        return ExchangeBuilder.directExchange(Constants.TOPIC_TSX_WHITEVEH).durable(true).build();
    }

    @Bean("addWhiteQueue")
    public Queue addWhiteQueue() {
        return QueueBuilder.durable(Constants.ADD_WHITE_QUEUE).build();
    }
    @Bean
    public Binding addWhiteBinding(@Qualifier("addWhiteQueue") Queue queue, @Qualifier("whiteExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.ADD_WHITE_KEY).noargs();
    }

    @Bean("delWhiteQueue")
    public Queue delWhiteQueue() {
        return QueueBuilder.durable(Constants.DEL_WHITE_QUEUE).build();
    }
    @Bean
    public Binding delWhiteBinding(@Qualifier("delWhiteQueue") Queue queue, @Qualifier("whiteExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.DEL_WHITE_KEY).noargs();
    }
}
