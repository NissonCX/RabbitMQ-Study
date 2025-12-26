package com.cqu.rabbitmqdemo.fanout;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class FanoutConsumer1 {
    @RabbitListener(queues = "fanout.queue1")
    public void receive(String message) {
        System.out.println("[Fanout Consumer 1] Received: " + message);
    }
}