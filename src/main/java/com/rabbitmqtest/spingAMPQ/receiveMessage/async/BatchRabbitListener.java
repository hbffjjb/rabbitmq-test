package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.List;

public class BatchRabbitListener {
    @RabbitListener(queues = "queue-test",containerFactory = "simpleRabbitListenerContainerFactory")
    public void testHeader(List<Message> messages,
                           @Header(AmqpHeaders.LAST_IN_BATCH) Boolean last,
                           @Header(AmqpHeaders.BATCH_SIZE) Integer size){
        for (Message msg: messages) {
            System.out.println("received message :"+ msg);
        }
        System.out.println("last msg :"+last);
        System.out.println("every batch size："+size);
    }


}
