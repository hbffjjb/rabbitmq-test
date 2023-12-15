package com.rabbitmqtest.spingAMPQ.AMQPTemplate;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ChannelListener;

public class ConnectionListener {
    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;

    public ConnectionListener(ProduceCachingConnectionFactory produceCachingConnectionFactory){
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }

    public void ChannelListenerTest(){
        CachingConnectionFactory factory = produceCachingConnectionFactory.createCCF();
        factory.addChannelListener(new ChannelListener() {
            @Override
            public void onCreate(Channel channel, boolean b) {
                System.out.println("connection created successfully");
            }
            @Override
            public void onShutDown(ShutdownSignalException signal) {
                ChannelListener.super.onShutDown(signal);
                System.out.println("消息："+signal.getMessage()+"丢失");
                System.out.println("丢失原因："+signal.getReason());
            }
        });
    }
}
