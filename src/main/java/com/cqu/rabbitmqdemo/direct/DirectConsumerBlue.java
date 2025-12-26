package com.cqu.rabbitmqdemo.direct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectConsumerBlue {
    @RabbitListener(queues = "direct.queue.blue")
    public void receive(String message) {
        System.out.println("[Direct Consumer Blue] Received: " + message);
    }
}

