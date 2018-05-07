package com.mydao.nsop.client.main.config;

import com.mydao.nsop.client.common.Constants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZYW
 * @date 2018/5/7
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue tsxJourneyQueue() {
        return new Queue(Constants.TOPIC_TSX_JOURNEY);
    }
}
