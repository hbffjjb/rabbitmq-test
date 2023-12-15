package com.rabbitmqtest.spingAMPQ.AMQPTemplate;

import com.rabbitmq.client.Channel;
import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannelFactory;

import java.util.concurrent.ExecutorService;

public class MultiThreadMessageSort {

    /**
     * 由于 RabbitMQ 的异步性质和对缓存 channel 的使用；不确定是否会使用同一 channel，因此不保证消息到达队列的顺序。
     * 为了解决这个用例，可以使用一个大小为 1 的有界 channel 缓存，以确保消息总在一个channel上发布，而且顺序会得到保证。
     */
    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;

    public MultiThreadMessageSort(ProduceCachingConnectionFactory produceCachingConnectionFactory){
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }

    public CachingConnectionFactory createCacheSizeChannel() {
        CachingConnectionFactory factory = produceCachingConnectionFactory.createCCF();
        CachingConnectionFactory publisherCF = (CachingConnectionFactory) factory.getPublisherConnectionFactory();
        publisherCF.setChannelCacheSize(1);
        publisherCF.setChannelCheckoutTimeout(10000);
        return publisherCF;
    }

}
