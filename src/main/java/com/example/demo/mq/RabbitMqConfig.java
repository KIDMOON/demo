package com.example.demo.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author：Kid date:2018/3/9
 */
@Configuration
public class RabbitMqConfig {

    public static final String QUEUE="queue";

    @Bean
    public Queue queue(){
        return  new Queue(QUEUE);
    }




}
