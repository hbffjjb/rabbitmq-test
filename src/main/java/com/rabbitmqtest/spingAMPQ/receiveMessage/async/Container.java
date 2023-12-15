package com.rabbitmqtest.spingAMPQ.receiveMessage.async;


import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import java.util.Collections;

public class Container {
    /**
     * 当配置容器时，基本上是在AMQP队列和 MessageListener 实例之间架起了桥梁。
     */
    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;
    private final MyMessageListener messageListener;
    public Container(ProduceCachingConnectionFactory produceCachingConnectionFactory, MyMessageListener messageListener) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
        this.messageListener = messageListener;
    }


    public SimpleMessageListenerContainer createSimpleContainer(MyMessageListener delegate){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(produceCachingConnectionFactory.createCCF());
        container.setQueueNames("queue-test");
        //不加适配器的消息监听器
        container.setMessageListener(messageListener);
        //加适配器
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(delegate);
        messageListenerAdapter.setDefaultListenerMethod("handleMessage");
        container.setMessageListener(messageListenerAdapter);
        //设置消费者优先级
        container.setConsumerArguments(Collections.singletonMap("x-priority", 10));
        return container;
    }
}
