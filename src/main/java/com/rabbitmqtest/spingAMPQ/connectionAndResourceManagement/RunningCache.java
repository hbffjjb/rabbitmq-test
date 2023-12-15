package com.rabbitmqtest.spingAMPQ.connectionAndResourceManagement;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;

import java.util.Properties;

public class RunningCache {
    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;


    public RunningCache(ProduceCachingConnectionFactory produceCachingConnectionFactory) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }

    public void testCache(){
        CachingConnectionFactory factory = produceCachingConnectionFactory.createCCF();
        Properties cacheProperties = factory.getCacheProperties();
        String connectionName = cacheProperties.getProperty("connectionName");
        String idleChannelsTxHighWater = cacheProperties.getProperty("idleChannelsTxHighWater");
        System.out.println(cacheProperties+" : "+idleChannelsTxHighWater);
    }


}
