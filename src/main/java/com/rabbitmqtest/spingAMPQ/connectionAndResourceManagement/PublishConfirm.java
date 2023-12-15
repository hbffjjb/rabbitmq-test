package com.rabbitmqtest.spingAMPQ.connectionAndResourceManagement;

import com.rabbitmq.client.Channel;
import org.apache.commons.logging.Log;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ChannelListener;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.support.ConditionalExceptionLogger;

import java.util.Properties;

public class PublishConfirm {

    public Connection createConnectionFactoryWithPublishConfirm(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setCloseExceptionLogger(new ConditionalExceptionLogger() {
            @Override
            public void log(Log log, String s, Throwable throwable) {
            }
        });
        connectionFactory.addConnectionListener(new ConnectionListener() {
            @Override
            public void onCreate(Connection connection) {

            }
        });
        connectionFactory.addChannelListener(new ChannelListener() {
            @Override
            public void onCreate(Channel channel, boolean b) {

            }
        });
        connectionFactory.setConnectionNameStrategy((factory)-> "connection_name");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        Connection connection = connectionFactory.createConnection();
        return connection;

    }
}
