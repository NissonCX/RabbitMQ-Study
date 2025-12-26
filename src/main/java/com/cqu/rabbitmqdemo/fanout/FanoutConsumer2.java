package com.cqu.rabbitmqdemo.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer2 {
    @RabbitListener(queues = "fanout.queue2")
    public void receive(String message) {
        System.out.println("[Fanout Consumer 2] Received: " + message);
    }
}

