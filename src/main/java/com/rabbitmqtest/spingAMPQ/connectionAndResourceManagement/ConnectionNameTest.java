package com.rabbitmqtest.spingAMPQ.connectionAndResourceManagement;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionNameTest {
    /**
     * 连接的命名
     * connectionFactory.setConnectionNameStrategy((factory)-> "connection_name");
     * 依赖注入：SimplePropertyValueConnectionNameStrategy("name")
     */

    public Connection createConnectionWithName(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setConnectionNameStrategy((factory)-> "connection_name");
        Connection connection = connectionFactory.createConnection();
        return connection;
    }

    @Bean
    public SimplePropertyValueConnectionNameStrategy createConnectionName(){
        return new SimplePropertyValueConnectionNameStrategy("connection_name");
    }

    @Bean
    public Connection createConnectionWithConnectionName(ConnectionNameStrategy cns){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setConnectionNameStrategy(cns);
        Connection connection = connectionFactory.createConnection();
        return connection;
    }
}
