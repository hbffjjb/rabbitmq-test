package com.rabbitmqtest.spingAMPQ.utils;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProduceCachingConnectionFactory {
    @Bean
    public CachingConnectionFactory createCCF(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("118.31.5.247:5673,118.31.5.247:5674,118.31.5.247:5675");
        return connectionFactory;
    }
}
