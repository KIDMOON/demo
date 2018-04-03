package com.example.demo.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author：Kid date:2018/3/9
 */
@Component
public class Receive {

    @RabbitListener(queues="queue")
    public void process(String str) {
        System.out.println("Receive:"+str);
    }
}
