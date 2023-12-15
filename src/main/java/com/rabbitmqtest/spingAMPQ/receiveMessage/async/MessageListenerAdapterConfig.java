package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class MessageListenerAdapterConfig {
    /**
     * 消息监听适配器，其本身就是一种监听器，只不过它在消息监听方法中将消息监听业务委托给了指定对象的指定方法。
     * MessageListenerAdapter 是一个常见的适配器类，它可以将你的消息处理器方法与 RabbitMQ 消息的类型和内容进行映射。
     * 这样，你就不需要直接实现 MessageListener 接口，而是可以专注于编写业务逻辑代码
     */

    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;
    public MessageListenerAdapterConfig(ProduceCachingConnectionFactory produceCachingConnectionFactory) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(@Qualifier("myMessageListener") MyMessageListener delegate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(produceCachingConnectionFactory.createCCF());
        container.setQueueNames("test-queue");
        // 创建适配器,设置委托的监听器
        MessageListenerAdapter adapter = new MessageListenerAdapter(delegate);
        // 设置默认的消息监听方法名称
        adapter.setDefaultListenerMethod("handleMessage");
        // 设置MessageListener(消息监听器)
        container.setMessageListener(adapter);
        return container;
    }
}
