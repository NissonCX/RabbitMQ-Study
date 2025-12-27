# RabbitMQ 高级使用示例与最佳实践

本项目展示了RabbitMQ的多种高级用法和最佳实践，包括死信队列、延迟消息、优先级队列等功能。

## 功能列表

### 1. 死信队列 (Dead Letter Queue)
- 用途：处理无法被正常消费的消息
- 配置：TTL过期、队列满、消息被拒绝
- 应用场景：订单超时处理、失败消息重试

### 2. 延迟消息 (Delayed Message)
- 用途：在指定时间后处理消息
- 需要安装：rabbitmq-delayed-message-exchange插件
- 应用场景：订单超时取消、优惠券到期提醒

### 3. 消息优先级 (Priority Queue)
- 用途：按优先级处理消息
- 配置：队列最大优先级为10
- 应用场景：紧急订单处理、VIP客户服务

### 4. 消息确认机制 (Publisher Confirm)
- 用途：确保消息可靠发送
- 配置：publisher-confirm-type: correlated
- 应用场景：金融交易、重要业务消息

### 5. 消息广播 (Fanout Exchange)
- 用途：将消息发送到所有绑定的队列
- 应用场景：系统通知、缓存更新

## API 接口

### 高级消息功能接口
- `POST /advanced/ttl` - 发送TTL消息
- `POST /advanced/priority` - 发送优先级消息
- `POST /advanced/delayed` - 发送延迟消息
- `POST /advanced/reliable` - 发送可靠消息
- `POST /advanced/broadcast` - 广播消息
- `POST /advanced/custom` - 发送自定义消息

### 订单业务接口
- `POST /order/create` - 创建订单
- `POST /order/pay` - 处理支付
- `POST /order/cancel` - 取消订单
- `POST /order/complete` - 完成订单

### 测试接口
- `POST /test/all-features` - 测试所有高级功能
- `POST /test/dead-letter` - 测试死信队列

## 最佳实践

### 1. 消息可靠性
- 启用发布确认（publisher confirm）
- 使用事务或发送确认机制
- 实现消息重试逻辑

### 2. 消息消费
- 使用手动确认模式
- 合理设置预取数量（prefetch）
- 处理异常情况，避免消息丢失

### 3. 队列设计
- 使用适当的交换机类型
- 设置合理的队列参数（TTL、优先级等）
- 监控队列长度，避免积压

### 4. 性能优化
- 合理设置连接池
- 使用批量操作
- 优化消息序列化

## 配置说明

### application.yml 重要配置
```yaml
spring:
  rabbitmq:
    # 连接配置
    connection-timeout: 1s
    # 发布确认和返回
    publisher-confirm-type: correlated  # 启用发布确认
    publisher-returns: true             # 启用发布返回
    # 消费者配置
    listener:
      simple:
        prefetch: 1                     # 每次只处理一条消息
        retry:
          enabled: true                 # 启用消费重试
```

### 队列参数说明
- `x-message-ttl`: 消息TTL（毫秒）
- `x-dead-letter-exchange`: 死信交换机
- `x-dead-letter-routing-key`: 死信路由键
- `x-max-priority`: 最大优先级
- `x-delay`: 延迟时间（毫秒，需要插件）

## 使用示例

### 发送延迟消息
```bash
curl -X POST "http://localhost:8080/advanced/delayed?message=延迟消息测试&delay=10000"
```

### 发送优先级消息
```bash
curl -X POST "http://localhost:8080/advanced/priority?message=高优先级消息&priority=10"
```

### 创建订单（业务场景）
```bash
curl -X POST "http://localhost:8080/order/create?orderId=ORDER123&userId=USER456&amount=99.99"
```

## 注意事项

1. **延迟消息插件**：延迟消息功能需要安装rabbitmq-delayed-message-exchange插件
2. **消息序列化**：推荐使用JSON格式，便于调试和维护
3. **异常处理**：消费者必须处理异常情况，避免消息丢失
4. **监控告警**：对队列长度和消费延迟进行监控
5. **资源管理**：及时关闭连接和通道，避免资源泄露

## 常见问题

### 1. 消息丢失
- 原因：未启用发布确认或消费确认
- 解决：配置publisher confirm和manual ack

### 2. 消息积压
- 原因：消费能力不足
- 解决：增加消费者数量或优化消费逻辑

### 3. 死循环
- 原因：消息处理失败后重新入队
- 解决：设置最大重试次数或使用死信队列

这些示例展示了RabbitMQ在实际项目中的高级应用，包括可靠性保证、性能优化和常见问题的解决方案。