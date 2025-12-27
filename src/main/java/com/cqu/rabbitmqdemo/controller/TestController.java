package com.cqu.rabbitmqdemo.controller;

import com.cqu.rabbitmqdemo.advanced.AdvancedProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 测试控制器 - 用于演示各种RabbitMQ高级功能
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final AdvancedProducer advancedProducer;

    /**
     * 测试所有高级功能
     */
    @PostMapping("/all-features")
    public String testAllFeatures() {
        // 发送TTL消息
        advancedProducer.sendTTLMessage("TTL测试消息", 15000);
        
        // 发送优先级消息
        advancedProducer.sendPriorityMessage("低优先级消息", 1);
        advancedProducer.sendPriorityMessage("高优先级消息", 10);
        
        // 发送延迟消息
        advancedProducer.sendDelayedMessage("延迟测试消息", 3000);
        
        // 发送可靠消息
        advancedProducer.sendReliableMessage("可靠测试消息");
        
        // 广播消息
        advancedProducer.broadcastMessage("广播测试消息");
        
        // 发送自定义消息
        advancedProducer.sendCustomMessage("自定义测试消息", "user123", "TEST_EVENT");
        
        return "所有高级功能测试消息已发送";
    }

    /**
     * 测试死信队列
     */
    @PostMapping("/dead-letter")
    public String testDeadLetter() {
        // 发送一个会过期的消息，触发死信队列
        advancedProducer.sendTTLMessage("这是一条会过期的消息", 1000); // 1秒后过期
        
        // 发送一个处理失败的消息，也会进入死信队列
        advancedProducer.sendTTLMessage("这是一条会处理失败的消息fail", 10000);
        
        return "死信队列测试消息已发送";
    }
}