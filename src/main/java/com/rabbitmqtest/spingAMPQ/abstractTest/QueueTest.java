package com.rabbitmqtest.spingAMPQ.abstractTest;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public class QueueTest {

    @Bean
    public Queue createQueue(){
        return new Queue("testQueue",true,false,true,null);
    }
}
