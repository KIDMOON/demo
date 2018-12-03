package com.example.demo.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.demo.mq.RabbitMqConfig.QUEUE;

/**
 * @author：Kid date:2018/3/9
 */
@Component
public class Sender{

    @Autowired
    private AmqpTemplate template;

    public void send() {
        template.convertAndSend(QUEUE,"hello,rabbit~");
    }

}