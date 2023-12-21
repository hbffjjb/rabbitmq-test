package com.rabbitmqtest.spingAMPQ.requestAndResponse;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplyListenerContainerTest {

    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;

    public ReplyListenerContainerTest(ProduceCachingConnectionFactory produceCachingConnectionFactory) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate amqpTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setMessageConverter(new SimpleMessageConverter());
        rabbitTemplate.setReplyAddress(replyQueue().getName());
        rabbitTemplate.setReplyTimeout(6000L);
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer replyMessageListenerContainer(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(produceCachingConnectionFactory.createCCF());
        container.setQueueNames(replyQueue().getName());
        container.setMessageListener(amqpTemplate());
        return container;
    }

    @Bean
    public Queue replyQueue(){
        return new Queue("reply-queue-test");
    }

}
