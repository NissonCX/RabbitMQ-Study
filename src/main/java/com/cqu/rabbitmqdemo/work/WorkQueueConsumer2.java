package com.cqu.rabbitmqdemo.work;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueConsumer2 {
    @RabbitListener(queues = "work.queue")
    public void receive(String message) {
        System.out.println("[Work Consumer 2] Received: " + message);
    }
}

