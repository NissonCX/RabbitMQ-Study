package com.cqu.rabbitmqdemo.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumerAll {
    @RabbitListener(queues = "topic.queue.all")
    public void receive(String message) {
        System.out.println("[Topic Consumer All] Received: " + message);
    }
}

