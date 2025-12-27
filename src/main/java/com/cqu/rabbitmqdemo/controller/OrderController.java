package com.cqu.rabbitmqdemo.controller;

import com.cqu.rabbitmqdemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器 - 演示RabbitMQ在订单业务中的应用
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public String createOrder(
            @RequestParam String orderId,
            @RequestParam String userId,
            @RequestParam double amount) {
        return orderService.createOrder(orderId, userId, amount);
    }

    /**
     * 处理订单支付
     */
    @PostMapping("/pay")
    public String processPayment(
            @RequestParam String orderId,
            @RequestParam String paymentId) {
        return orderService.processPayment(orderId, paymentId);
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam String orderId) {
        return orderService.cancelOrder(orderId);
    }

    /**
     * 完成订单
     */
    @PostMapping("/complete")
    public String completeOrder(@RequestParam String orderId) {
        return orderService.completeOrder(orderId);
    }
}