package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;

public class ContainerFactory {

    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;

    public ContainerFactory(ProduceCachingConnectionFactory produceCachingConnectionFactory) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }


    //带有批处理功能的容器工厂
    @Bean("simpleRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory createSRLCFactory(){
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(produceCachingConnectionFactory.createCCF());
        containerFactory.setBatchListener(true);
        return containerFactory;
    }





}
