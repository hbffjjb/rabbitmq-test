package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

public class ContainerFactory {

    private final ConsumerTag consumerTag;

    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;

    public ContainerFactory(ConsumerTag consumerTag, ProduceCachingConnectionFactory produceCachingConnectionFactory) {
        this.consumerTag = consumerTag;
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }


    //带有批处理功能的容器工厂
    ////SimpleRabbitListenerContainerFactory 内部内置了一个容器
    @Bean("simpleRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory createSRLCFactory(){
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(produceCachingConnectionFactory.createCCF());
        containerFactory.setConsumerTagStrategy(consumerTag);
        containerFactory.setBatchListener(true);
        //设置闲置消费者的检测
        containerFactory.setIdleEventInterval(60000L);
        containerFactory.setBatchSize(2);
        containerFactory.setConsumerBatchEnabled(true);
        return containerFactory;
    }

    //用RabbitListenerEndpointRegistry注册容器
    @Bean("rabbitListenerEndpointRegistry")
    public SimpleMessageListenerContainer createSMLCByEndpoint(
            SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory){
        SimpleRabbitListenerEndpoint listenerEndpoint = new SimpleRabbitListenerEndpoint();
        listenerEndpoint.setQueueNames("endpoint-queue");
        listenerEndpoint.setMessageListener((message -> {
            System.out.println("received message："+ Arrays.toString(message.getBody()));
        }));
        return simpleRabbitListenerContainerFactory.createListenerContainer(listenerEndpoint);
    }

    //创建容器后添加监听器
    @Bean()
    public SimpleMessageListenerContainer createSMLCByFactory(
            SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory){
        SimpleMessageListenerContainer listenerContainer = simpleRabbitListenerContainerFactory.createListenerContainer();
        listenerContainer.setQueueNames("container-queue");
        listenerContainer.setMessageListener((message)->{
            System.out.println("received message："+ Arrays.toString(message.getBody()));
        });
        listenerContainer.setIdleEventInterval(6000L);
        return listenerContainer;

    }



}
