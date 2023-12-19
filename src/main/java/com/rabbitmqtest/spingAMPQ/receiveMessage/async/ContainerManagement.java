package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Set;

public class ContainerManagement {

    @Bean("rabbitListenerEndpointRegistry")
    public RabbitListenerEndpointRegistry createRLER(){
        RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry = new RabbitListenerEndpointRegistry();
        //获取所有的容器集合
        Collection<MessageListenerContainer> containers = rabbitListenerEndpointRegistry.getListenerContainers();
        for (MessageListenerContainer container : containers) {

        }
        //获取所有的containerId
        Set<String> listenerContainerIds = rabbitListenerEndpointRegistry.getListenerContainerIds();
        for (String id: listenerContainerIds) {
            System.out.println(id);
        }
        return rabbitListenerEndpointRegistry;
    }

    public void test(){
    }


}
