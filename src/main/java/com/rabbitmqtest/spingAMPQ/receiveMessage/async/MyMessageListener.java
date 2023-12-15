package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import com.rabbitmq.client.Channel;
import com.rabbitmqtest.spingAMPQ.receiveMessage.BasicQueueName;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("myMessageListener")
public class MyMessageListener implements MessageListener ,ChannelAwareMessageListener {

    /**
     * 不依赖于channel的messageListener
     */
    @Override
    public void onMessage(Message message) {
        System.out.println("received message:"+ Arrays.toString(message.getBody()));
    }

    /**
     * 依赖于channel的messageListener
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("channel:"+channel+" received message:"+ Arrays.toString(message.getBody()));
    }

    /**
     * 容
     */
    @RabbitListener(queues = "batchQueue-test",containerFactory = "simpleRabbitListenerContainerFactory")
    public void handleMessage(List<Message> messages){
        for (Message message: messages) {
            System.out.println("received message: "+message);
        }
    }

    /**
     * 自定义的messageListener,用于适配器中的delegate
     */
    public Boolean handleMessage(String message){
        System.out.println("received message:"+message);
        return true;
    }

    /**
     * 自定义的batchMessageListener,用于适配器中的delegate
     */
    public Boolean handleBatchMessage(List<String> messages){
        for (String message : messages) {
            System.out.println("received message:" + message);
        }
        return true;
    }

}
