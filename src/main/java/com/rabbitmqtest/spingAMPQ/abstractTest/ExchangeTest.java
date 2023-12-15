package com.rabbitmqtest.spingAMPQ.abstractTest;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

public class ExchangeTest {
    @Bean
    public DirectExchange createDirectExchange(){
        return new DirectExchange("",true,false,null);
    }

    @Bean
    public FanoutExchange createFanoutExchange(){
        return new FanoutExchange("",false,false,null);
    }

    @Bean
    public TopicExchange createTopicExchange(){
        return new TopicExchange("",true,false,null);
    }
}
