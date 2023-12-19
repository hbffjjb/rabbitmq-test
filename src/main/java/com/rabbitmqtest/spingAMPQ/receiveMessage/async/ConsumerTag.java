package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConsumerTag implements ConsumerTagStrategy {
    @Override
    public String createConsumerTag(String queue) {
        return "consumer-"+queue;
    }
}
