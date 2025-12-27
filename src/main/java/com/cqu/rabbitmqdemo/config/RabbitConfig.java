package com.cqu.rabbitmqdemo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue simpleQueue() {
        return new Queue("simple.queue");
    }

    @Bean
    public Queue workQueue() {
        return new Queue("work.queue");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout.exchange");
    }

    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanout.queue1");
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanout.queue2");
    }

    @Bean
    public Binding bindingFanout1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    public Binding bindingFanout2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct.exchange");
    }

    @Bean
    public Queue directQueueRed() {
        return new Queue("direct.queue.red");
    }

    @Bean
    public Queue directQueueBlue() {
        return new Queue("direct.queue.blue");
    }

    @Bean
    public Binding bindingDirectRed(Queue directQueueRed, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueRed).to(directExchange).with("red");
    }

    @Bean
    public Binding bindingDirectBlue(Queue directQueueBlue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueBlue).to(directExchange).with("blue");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic.exchange");
    }

    @Bean
    public Queue topicQueueChina() {
        return new Queue("topic.queue.china");
    }

    @Bean
    public Queue topicQueueAll() {
        return new Queue("topic.queue.all");
    }

    @Bean
    public Binding bindingTopicChina(Queue topicQueueChina, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueueChina).to(topicExchange).with("china.#");
    }

    @Bean
    public Binding bindingTopicAll(Queue topicQueueAll, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueueAll).to(topicExchange).with("*.*");
    }
}