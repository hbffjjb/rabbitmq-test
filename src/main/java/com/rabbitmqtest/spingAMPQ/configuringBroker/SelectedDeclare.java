package com.rabbitmqtest.spingAMPQ.configuringBroker;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SelectedDeclare {

    @Bean("admin1")
    public RabbitAdmin admin1(){
        return new RabbitAdmin(new CachingConnectionFactory());
    }

    @Bean("admin2")
    public RabbitAdmin admin2(){
        return new RabbitAdmin(new CachingConnectionFactory());
    }

    @Bean("queue-test")
    public Queue queue1(){
        Queue queue1 = new Queue("queue1");
        queue1.setAdminsThatShouldDeclare(admin1());
        return queue1;
    }

    @Bean("selected-exchange")
    public DirectExchange exchange(){
        DirectExchange exchange = new DirectExchange("ex", false, false, null);
        exchange.setAdminsThatShouldDeclare(admin1());
        return exchange;
    }

    @Bean
    public Binding binding(){
        Binding binding = new Binding("queue", Binding.DestinationType.QUEUE, "exchange", "routingKey", null);
        binding.setAdminsThatShouldDeclare(admin1());
        return binding;
    }


}
