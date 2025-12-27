package com.cqu.rabbitmqdemo.advanced;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AdvancedConsumer {

    /**
     * 死信队列消费者
     */
    @RabbitListener(queues = "dead.letter.queue")
    public void handleDeadLetterMessage(String message) {
        log.error("处理死信消息: {}", message);
        // 这里可以进行失败消息的特殊处理，如记录日志、发送告警等
    }

    /**
     * TTL队列消费者
     */
    @RabbitListener(queues = "ttl.queue")
    public void handleTTLMessage(String message, Channel channel, Message springMessage) {
        try {
            log.info("处理TTL消息: {}", message);
            // 模拟处理失败，将消息重新入队或进入死信队列
            if (message.contains("fail")) {
                // 手动拒绝消息并重新入队
                channel.basicNack(springMessage.getMessageProperties().getDeliveryTag(), false, true);
                log.warn("TTL消息处理失败，重新入队: {}", message);
            } else {
                // 手动确认消息
                channel.basicAck(springMessage.getMessageProperties().getDeliveryTag(), false);
                log.info("TTL消息处理成功: {}", message);
            }
        } catch (IOException e) {
            log.error("处理TTL消息时发生错误", e);
            try {
                channel.basicNack(springMessage.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException ex) {
                log.error("确认消息时发生错误", ex);
            }
        }
    }

    /**
     * 延迟队列消费者
     */
    @RabbitListener(queues = "delayed.queue")
    public void handleDelayedMessage(String message) {
        log.info("处理延迟消息: {}", message);
        // 在这里处理延迟任务，如订单超时取消、优惠券过期等
    }

    /**
     * 优先级队列消费者
     */
    @RabbitListener(queues = "priority.queue")
    public void handlePriorityMessage(String message) {
        log.info("处理优先级消息: {}", message);
        // 高优先级消息会先被消费
    }

    /**
     * 可靠消息消费者
     */
    @RabbitListener(queues = "reliable.queue")
    public void handleReliableMessage(String message, Channel channel, Message springMessage,
                                      @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        try {
            log.info("处理可靠消息: {}, 路由键: {}", message, routingKey);
            
            // 从消息头获取自定义属性
            String userId = (String) springMessage.getMessageProperties().getHeaders().get("user_id");
            String businessType = (String) springMessage.getMessageProperties().getHeaders().get("business_type");
            
            if (userId != null) {
                log.info("用户ID: {}, 业务类型: {}", userId, businessType);
            }
            
            // 模拟业务处理
            Thread.sleep(100); // 模拟处理时间
            
            // 手动确认消息
            channel.basicAck(springMessage.getMessageProperties().getDeliveryTag(), false);
            log.info("可靠消息处理成功: {}", message);
        } catch (Exception e) {
            log.error("处理可靠消息时发生错误", e);
            try {
                // 拒绝消息，不重新入队
                channel.basicNack(springMessage.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException ex) {
                log.error("确认消息时发生错误", ex);
            }
        }
    }

    /**
     * 广播队列1消费者
     */
    @RabbitListener(queues = "broadcast.queue1")
    public void handleBroadcastMessage1(String message) {
        log.info("广播队列1收到消息: {}", message);
    }

    /**
     * 广播队列2消费者
     */
    @RabbitListener(queues = "broadcast.queue2")
    public void handleBroadcastMessage2(String message) {
        log.info("广播队列2收到消息: {}", message);
    }
}