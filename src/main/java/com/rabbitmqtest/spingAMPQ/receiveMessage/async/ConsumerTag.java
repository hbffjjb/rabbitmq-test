package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import org.springframework.amqp.support.ConsumerTagStrategy;

public class ConsumerTag implements ConsumerTagStrategy {
    @Override
    public String createConsumerTag(String queue) {
        return "consumer-"+queue;
    }
}
