package com.rabbitmqtest.spingAMPQ.modifyMessage;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessagePostProcessorTest implements MessagePostProcessor {
    @Bean
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setHeader("myHeader","value");
        return message;
    }

    @Bean
    public RabbitTemplate createTemplateWithMPP(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setBeforePublishPostProcessors(this);
        rabbitTemplate.setAfterReceivePostProcessors(this::postProcessMessage);
        return rabbitTemplate;
    }

}
