package com.cqu.rabbitmqdemo.work;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueProducer implements CommandLineRunner {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) {
        for (int i = 1; i <= 10; i++) {
            String message = "WorkQueue Message " + i;
            rabbitTemplate.convertAndSend("work.queue", message);
            System.out.println("[Work Producer] Sent: " + message);
        }
    }
}
