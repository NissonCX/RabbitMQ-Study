package com.cqu.rabbitmqdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AdvancedRabbitConfig {

    // 死信队列配置
    // 死信交换机
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("dead.letter.exchange");
    }

    // 死信队列
    @Bean
    public Queue deadLetterQueue() {
        return new Queue("dead.letter.queue");
    }

    // 死信队列绑定
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with("dead.letter.routing.key");
    }

    // 带有TTL的普通队列，消息过期后进入死信队列
    @Bean
    public Queue ttlQueue() {
        Map<String, Object> args = new HashMap<>();
        // 设置消息过期时间（毫秒）
        args.put("x-message-ttl", 10000);
        // 设置死信交换机
        args.put("x-dead-letter-exchange", "dead.letter.exchange");
        // 设置死信路由键
        args.put("x-dead-letter-routing-key", "dead.letter.routing.key");
        return QueueBuilder.durable("ttl.queue").withArguments(args).build();
    }

    // 延迟队列 - 使用插件方式（需要安装rabbitmq-delayed-message-exchange插件）
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("delayed.exchange", "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue delayedQueue() {
        return new Queue("delayed.queue");
    }

    @Bean
    public Binding delayedBinding() {
        return BindingBuilder.bind(delayedQueue())
                .to(delayedExchange())
                .with("delayed.routing.key")
                .noargs();
    }

    // 消息优先级队列
    @Bean
    public Queue priorityQueue() {
        Map<String, Object> args = new HashMap<>();
        // 设置最大优先级
        args.put("x-max-priority", 10);
        return QueueBuilder.durable("priority.queue").withArguments(args).build();
    }

    // 确保消息被正确处理的队列
    @Bean
    public Queue reliableQueue() {
        return new Queue("reliable.queue");
    }

    // 扇出交换机用于广播消息
    @Bean
    public FanoutExchange broadcastExchange() {
        return new FanoutExchange("broadcast.exchange");
    }

    // 广播队列1
    @Bean
    public Queue broadcastQueue1() {
        return new Queue("broadcast.queue1");
    }

    // 广播队列2
    @Bean
    public Queue broadcastQueue2() {
        return new Queue("broadcast.queue2");
    }

    // 广播绑定
    @Bean
    public Binding broadcastBinding1() {
        return BindingBuilder.bind(broadcastQueue1())
                .to(broadcastExchange());
    }

    @Bean
    public Binding broadcastBinding2() {
        return BindingBuilder.bind(broadcastQueue2())
                .to(broadcastExchange());
    }
}