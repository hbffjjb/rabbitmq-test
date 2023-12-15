package com.rabbitmqtest.spingAMPQ.abstractTest;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;

public class BindingTest {

    private QueueTest queueTest;
    private ExchangeTest exchangeTest;

    @Bean
    public Binding createDirectBinding(){
        return BindingBuilder.bind(queueTest.createQueue()).to(exchangeTest.createDirectExchange()).with("directExchange");
    }

    @Bean
    public Binding createFanoutBinding(){
        return BindingBuilder.bind(queueTest.createQueue()).to(exchangeTest.createFanoutExchange());
    }

    @Bean
    public Binding createTopicBinding(){
        return BindingBuilder.bind(queueTest.createQueue()).to(exchangeTest.createTopicExchange()).with("top.*");
    }
}
