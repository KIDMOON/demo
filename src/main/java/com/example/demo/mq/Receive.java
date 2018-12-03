package com.example.demo.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.demo.mq.RabbitMqConfig.QUEUE;

/**
 * @author：Kid date:2018/3/9
 */
@Component
public class Receive {

    @RabbitListener(queues=QUEUE)
    public void process(String str) {
        System.out.println("Receive:"+str);
    }
}
