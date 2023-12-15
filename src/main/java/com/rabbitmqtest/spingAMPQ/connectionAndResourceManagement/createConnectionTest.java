package com.rabbitmqtest.spingAMPQ.connectionAndResourceManagement;

import org.springframework.amqp.rabbit.connection.*;
import org.springframework.context.annotation.Bean;

public class createConnectionTest {
    /**
     *三种连接工厂：pooledChannelConnectionFactory、ThreadChannelConnectionFactory、CachingConnectionFactory
     */

    @Bean
    public Connection createPooledChannelConnectionFactory(){

        com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();
        connectionFactory.setHost("localhost");
        PooledChannelConnectionFactory pcf = new PooledChannelConnectionFactory(connectionFactory);
        pcf.setPoolConfigurer((pool,tx)->{
            if (tx){
                System.out.println("事务channel");
            }else {
                System.out.println("非事务channel");
            }
        });
        return pcf.createConnection();
    }

    @Bean
    public Connection createThreadChannelConnectionFactory(){
        com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();
        ThreadChannelConnectionFactory threadChannelConnectionFactory = new ThreadChannelConnectionFactory(connectionFactory);
        threadChannelConnectionFactory.setHost("localhost");
        Connection connection = threadChannelConnectionFactory.createConnection();
        return connection;
    }

    @Bean
    public Connection createCachingConnectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("118.31.5.247:15601,118.31.5.247:15602,118.31.5.247:15603");
        connectionFactory.setConnectionNameStrategy(factory -> "rabbitmq_cluster_Connection");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory.createConnection();
    }



}
