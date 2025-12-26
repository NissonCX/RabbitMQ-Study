package com.cqu.rabbitmqdemo.direct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DirectProducer implements CommandLineRunner {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) {
        rabbitTemplate.convertAndSend("direct.exchange", "red", "Direct Message: red");
        rabbitTemplate.convertAndSend("direct.exchange", "blue", "Direct Message: blue");
        System.out.println("[Direct Producer] Sent: red, blue");
    }
}
