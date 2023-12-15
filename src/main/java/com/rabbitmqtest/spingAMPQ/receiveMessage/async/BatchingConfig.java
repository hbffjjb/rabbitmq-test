package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;

public class BatchingConfig {
    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;


    public BatchingConfig(ProduceCachingConnectionFactory produceCachingConnectionFactory) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }

    @Bean
    public SimpleMessageListenerContainer createBatchMessageContainer(MyMessageListener delegate){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(produceCachingConnectionFactory.createCCF());
        container.setQueueNames("queueName");
        //加适配器
        MessageListenerAdapter adapter = new MessageListenerAdapter(delegate);
        adapter.setDefaultListenerMethod("handleBatchMessage");
        container.setMessageListener(adapter);

        container.setDeBatchingEnabled(true);
        container.setConsumerBatchEnabled(true);
        container.setBatchSize(10);
        return container;
    }
}
