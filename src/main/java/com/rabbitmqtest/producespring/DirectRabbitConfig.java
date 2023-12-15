package com.rabbitmqtest.producespring;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {
    @Bean
    public DirectExchange testDirectExchange(){
        return new DirectExchange("directExchange",false,false,null);
    }

    @Bean
    public Queue testQueue(){
        return new Queue("testQueue",false,false,false,null);
    }

    @Bean
    public Binding testBinding(){
        return BindingBuilder.bind(testQueue()).to(testDirectExchange()).with("foo.*");
    }

}
