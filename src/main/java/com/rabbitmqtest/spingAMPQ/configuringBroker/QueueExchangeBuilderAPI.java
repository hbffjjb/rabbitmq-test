package com.rabbitmqtest.spingAMPQ.configuringBroker;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

public class QueueExchangeBuilderAPI {

    @Bean
    public Queue queue(){
        return QueueBuilder.durable("test-builder-api")
                .autoDelete()
                .exclusive()
                .withArguments(Collections.singletonMap("key1","value1"))
                .build();
    }

    @Bean
    public Exchange exchange(){
        return ExchangeBuilder.directExchange("test-builder-api")
                .autoDelete()
                .internal()     //客户端不会使用该exchange，用于死信exchange
                .withArguments(Collections.singletonMap("key1","value1"))
                .build();
    }


}
