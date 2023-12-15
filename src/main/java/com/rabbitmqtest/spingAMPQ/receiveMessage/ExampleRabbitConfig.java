package com.rabbitmqtest.spingAMPQ.receiveMessage;

import com.rabbitmqtest.spingAMPQ.receiveMessage.async.MyMessageListener;
import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class ExampleRabbitConfig {
    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;

    private final MyMessageListener messageListener;

    public ExampleRabbitConfig(ProduceCachingConnectionFactory produceCachingConnectionFactory, MyMessageListener messageListener) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
        this.messageListener = messageListener;
    }

    @Bean
    public SimpleMessageListenerContainer createSMLC(){
        SimpleMessageListenerContainer SMLC = new SimpleMessageListenerContainer();
        SMLC.setConnectionFactory(produceCachingConnectionFactory.createCCF());
        SMLC.setQueueNames("test-Queue");
        SMLC.setConsumerBatchEnabled(true);
        SMLC.setMessageListener(messageListener);
        return SMLC;
    }

}
