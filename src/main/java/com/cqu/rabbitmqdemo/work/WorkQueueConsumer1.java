package com.cqu.rabbitmqdemo.work;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class WorkQueueConsumer1 {
    @RabbitListener(queues = "work.queue")
    public void receive(String message) {
        System.out.println("[Work Consumer 1] Received: " + message);
    }
}
