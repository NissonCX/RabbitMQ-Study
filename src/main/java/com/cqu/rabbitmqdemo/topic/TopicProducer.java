package com.cqu.rabbitmqdemo.topic;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TopicProducer implements CommandLineRunner {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) throws Exception {
        rabbitTemplate.convertAndSend("topic.exchange", "china.news", "Topic Message: china.news");
        rabbitTemplate.convertAndSend("topic.exchange", "us.weather", "Topic Message: us.weather");
        System.out.println("[Topic Producer] Sent: china.news, us.weather");
    }
}

