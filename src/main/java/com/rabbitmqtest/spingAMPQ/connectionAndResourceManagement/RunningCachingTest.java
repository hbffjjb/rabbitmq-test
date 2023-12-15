package com.rabbitmqtest.spingAMPQ.connectionAndResourceManagement;


import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

import java.util.Properties;

public class RunningCachingTest {

    private final static String connection_name = "test";

    public String testRunningCaching(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        connectionFactory.setConnectionNameStrategy((factory)->connection_name);
        Properties cacheProperties = connectionFactory.getCacheProperties();
        String connectionName = cacheProperties.getProperty("connectionName");
        if (connection_name.equals(connectionName)){
            return "ok";
        }else
            return "error";
    }
}
