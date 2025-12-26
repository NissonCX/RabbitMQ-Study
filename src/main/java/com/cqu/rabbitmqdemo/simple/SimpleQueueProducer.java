package com.cqu.rabbitmqdemo.simple;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SimpleQueueProducer implements CommandLineRunner {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) {
        for (int i = 1; i <= 5; i++) {
            String message = "SimpleQueue Message " + i;
            rabbitTemplate.convertAndSend("simple.queue", message);
            System.out.println("[Producer] Sent: " + message);
        }
    }
}
