package com.cqu.rabbitmqdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Slf4j
@Configuration
public class ConfirmCallbackConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    private final RabbitTemplate rabbitTemplate;

    public ConfirmCallbackConfig(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        // 设置确认回调
        rabbitTemplate.setConfirmCallback(this);
        // 设置返回回调
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 发布确认回调
     * @param correlationData 消息关联数据
     * @param ack 确认状态
     * @param cause 失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.debug("消息发送成功，ID: {}", correlationData != null ? correlationData.getId() : "unknown");
        } else {
            log.error("消息发送失败，ID: {}，原因: {}", 
                     correlationData != null ? correlationData.getId() : "unknown", cause);
            // 在这里可以进行失败重试或其他处理
        }
    }

    /**
     * 消息返回回调（当消息无法路由到队列时触发）
     * @param returned 返回的消息
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息未能路由到队列，交换机: {}，路由键: {}，消息: {}", 
                 returned.getExchange(), returned.getRoutingKey(), 
                 new String(returned.getMessage().getBody()));
    }
}