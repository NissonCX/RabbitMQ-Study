package com.cqu.rabbitmqdemo.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumerChina {
    @RabbitListener(queues = "topic.queue.china")
    public void receive(String message) {
        System.out.println("[Topic Consumer China] Received: " + message);
    }
}

