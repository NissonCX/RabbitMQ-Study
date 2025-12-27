package com.cqu.rabbitmqdemo.controller;

import com.cqu.rabbitmqdemo.advanced.AdvancedProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/advanced")
@RequiredArgsConstructor
public class AdvancedMessageController {

    private final AdvancedProducer advancedProducer;

    /**
     * 发送TTL消息
     */
    @PostMapping("/ttl")
    public String sendTTLMessage(@RequestParam String message, @RequestParam(defaultValue = "10000") int ttl) {
        advancedProducer.sendTTLMessage(message, ttl);
        return "TTL消息已发送: " + message + ", TTL: " + ttl + "ms";
    }

    /**
     * 发送优先级消息
     */
    @PostMapping("/priority")
    public String sendPriorityMessage(@RequestParam String message, @RequestParam(defaultValue = "5") int priority) {
        advancedProducer.sendPriorityMessage(message, priority);
        return "优先级消息已发送: " + message + ", 优先级: " + priority;
    }

    /**
     * 发送延迟消息
     */
    @PostMapping("/delayed")
    public String sendDelayedMessage(@RequestParam String message, @RequestParam(defaultValue = "5000") long delay) {
        advancedProducer.sendDelayedMessage(message, delay);
        return "延迟消息已发送: " + message + ", 延迟: " + delay + "ms";
    }

    /**
     * 发送可靠消息
     */
    @PostMapping("/reliable")
    public String sendReliableMessage(@RequestParam String message) {
        advancedProducer.sendReliableMessage(message);
        return "可靠消息已发送: " + message;
    }

    /**
     * 广播消息
     */
    @PostMapping("/broadcast")
    public String broadcastMessage(@RequestParam String message) {
        advancedProducer.broadcastMessage(message);
        return "广播消息已发送: " + message;
    }

    /**
     * 发送自定义消息
     */
    @PostMapping("/custom")
    public String sendCustomMessage(
            @RequestParam String message,
            @RequestParam String userId,
            @RequestParam String businessType) {
        advancedProducer.sendCustomMessage(message, userId, businessType);
        return "自定义消息已发送: " + message + ", 用户ID: " + userId + ", 业务类型: " + businessType;
    }
}