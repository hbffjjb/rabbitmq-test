package com.rabbitmqtest.spingAMPQ.MessageConverters;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.swing.*;

@Configuration
public class SimpleMessageConverterTest {

    @Bean("mySmc")
    public SimpleMessageConverter createSMC(){
        SimpleMessageConverter smc = new SimpleMessageConverter();
        smc.setDefaultCharset("UTF-8");
        return smc;
    }

    @Bean
    @Primary    //确保自定义的messageConverter为主版本
    public RabbitTemplate myRabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setMessageConverter(createSMC());
        return rabbitTemplate;
    }




}
