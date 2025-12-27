package com.cqu.rabbitmqdemo.advanced;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdvancedProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送带有TTL（Time To Live）的消息
     */
    public void sendTTLMessage(String message, int ttl) {
        String routingKey = "ttl.queue";
        rabbitTemplate.convertAndSend("ttl.queue", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(ttl));
                return message;
            }
        });
        log.info("发送TTL消息: {}, TTL: {}ms", message, ttl);
    }

    /**
     * 发送优先级消息
     */
    public void sendPriorityMessage(String message, int priority) {
        rabbitTemplate.convertAndSend("priority.queue", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setPriority(priority);
                return message;
            }
        });
        log.info("发送优先级消息: {}, 优先级: {}", message, priority);
    }

    /**
     * 发送延迟消息（需要安装rabbitmq-delayed-message-exchange插件）
     */
    public void sendDelayedMessage(String message, long delayTime) {
        rabbitTemplate.convertAndSend("delayed.exchange", "delayed.routing.key", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay", delayTime);
                return message;
            }
        });
        log.info("发送延迟消息: {}, 延迟: {}ms", message, delayTime);
    }

    /**
     * 发送可靠消息（开启发布确认）
     */
    public void sendReliableMessage(String message) {
        String correlationId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(correlationId);
        
        rabbitTemplate.convertAndSend("reliable.queue", message, correlationData);
        log.info("发送可靠消息: {}, ID: {}", message, correlationId);
    }

    /**
     * 广播消息到多个队列
     */
    public void broadcastMessage(String message) {
        rabbitTemplate.convertAndSend("broadcast.exchange", "", message);
        log.info("广播消息: {}", message);
    }

    /**
     * 发送带有自定义属性的消息
     */
    public void sendCustomMessage(String message, String userId, String businessType) {
        rabbitTemplate.convertAndSend("reliable.queue", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("user_id", userId);
                message.getMessageProperties().setHeader("business_type", businessType);
                return message;
            }
        });
        log.info("发送自定义消息: {}, 用户ID: {}, 业务类型: {}", message, userId, businessType);
    }
}