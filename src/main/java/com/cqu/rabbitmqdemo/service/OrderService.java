package com.cqu.rabbitmqdemo.service;

import com.cqu.rabbitmqdemo.advanced.AdvancedProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 订单服务示例 - 展示RabbitMQ在实际业务中的应用
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final AdvancedProducer advancedProducer;

    /**
     * 创建订单
     */
    public String createOrder(String orderId, String userId, double amount) {
        log.info("创建订单 - 订单ID: {}, 用户ID: {}, 金额: {}", orderId, userId, amount);
        
        // 发送订单创建消息
        String orderMessage = String.format("订单创建 - ID: %s, 用户: %s, 金额: %.2f", orderId, userId, amount);
        advancedProducer.sendCustomMessage(orderMessage, userId, "ORDER_CREATED");
        
        // 发送延迟消息，用于订单超时检查（5分钟后检查订单状态）
        String timeoutCheckMessage = String.format("检查订单超时 - 订单ID: %s", orderId);
        advancedProducer.sendDelayedMessage(timeoutCheckMessage, 5 * 60 * 1000); // 5分钟
        
        return "订单创建成功: " + orderId;
    }

    /**
     * 处理订单支付
     */
    public String processPayment(String orderId, String paymentId) {
        log.info("处理订单支付 - 订单ID: {}, 支付ID: {}", orderId, paymentId);
        
        // 发送支付成功消息
        String paymentMessage = String.format("支付成功 - 订单ID: %s, 支付ID: %s", orderId, paymentId);
        advancedProducer.sendPriorityMessage(paymentMessage, 8); // 高优先级
        
        return "支付处理成功: " + orderId;
    }

    /**
     * 取消超时订单
     */
    public String cancelOrder(String orderId) {
        log.info("取消订单 - 订单ID: {}", orderId);
        
        // 发送订单取消消息
        String cancelMessage = String.format("订单取消 - 订单ID: %s", orderId);
        advancedProducer.sendTTLMessage(cancelMessage, 30000); // 30秒TTL
        
        return "订单已取消: " + orderId;
    }

    /**
     * 处理订单完成
     */
    public String completeOrder(String orderId) {
        log.info("完成订单 - 订单ID: {}", orderId);
        
        // 广播订单完成消息给多个服务
        String completionMessage = String.format("订单完成 - 订单ID: %s", orderId);
        advancedProducer.broadcastMessage(completionMessage);
        
        return "订单已完成: " + orderId;
    }
}