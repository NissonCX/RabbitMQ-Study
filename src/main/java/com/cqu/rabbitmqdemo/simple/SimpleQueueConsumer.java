package com.cqu.rabbitmqdemo.simple;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class SimpleQueueConsumer {
    @RabbitListener(queues = "simple.queue")
    public void receive(String message) {
        System.out.println("[Consumer] Received: " + message);
    }
}