package com.rabbitmqtest.spingAMPQ.QueueNamedContainerBroker;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NamedQueue {

    @Bean
    public Queue createQueue(){
        return new Queue("",false,true,true);
    }

    @Bean
    public SimpleMessageListenerContainer createContainer(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setQueues(createQueue());
        container.setMessageListener((message -> {
            System.out.println("received message："+message);
        }));
        //在队列不存在时仍然允许应用程序启动
        container.setMissingQueuesFatal(false);
        return container;
    }
}
