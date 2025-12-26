package com.cqu.rabbitmqdemo.direct;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class DirectConsumerRed {
    @RabbitListener(queues = "direct.queue.red")
    public void receive(String message) {
        System.out.println("[Direct Consumer Red] Received: " + message);
    }
}
