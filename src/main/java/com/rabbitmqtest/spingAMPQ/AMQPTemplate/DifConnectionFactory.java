package com.rabbitmqtest.spingAMPQ.AMQPTemplate;

import com.rabbitmq.client.Channel;
import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannelFactory;

import java.util.concurrent.ExecutorService;

public class DifConnectionFactory {
    /**
     * 从2.0.2版本开始，usePublisherConnection 是 CachingConnectionFactory 类的一个方法，它是 Spring AMQP 框架的一部分。
     * 这个方法用于指示 CachingConnectionFactory 是否应该为发布者（publisher）使用一个单独的连接工厂。
     * 在默认情况下，CachingConnectionFactory 使用同一个连接工厂来处理发布者和消费者的消息操作。
     * 但是，如果想要为发布者和消费者使用不同的连接工厂，可以通过调用 setPublisherConnectionFactory() 方法来设置一个单独的发布者连接工厂，
     * 并将 usePublisherConnection 设置为 true：
     */
    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;
    public DifConnectionFactory(ProduceCachingConnectionFactory produceCachingConnectionFactory) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }

    public CachingConnectionFactory createDifCcf(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        CachingConnectionFactory publisherConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setPublisherConnectionFactory(publisherConnectionFactory);
        return null;

    }


}
