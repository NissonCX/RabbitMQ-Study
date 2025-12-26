package com.cqu.rabbitmqdemo.fanout;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FanoutProducer implements CommandLineRunner {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) {
        for (int i = 1; i <= 3; i++) {
            String message = "Fanout Message " + i;
            rabbitTemplate.convertAndSend("fanout.exchange", "", message);
            System.out.println("[Fanout Producer] Sent: " + message);
        }
    }
}
