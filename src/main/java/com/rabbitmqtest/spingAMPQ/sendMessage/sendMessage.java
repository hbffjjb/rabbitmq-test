package com.rabbitmqtest.spingAMPQ.sendMessage;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import com.rabbitmqtest.spingAMPQ.utils.ProduceMessage;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.beans.PropertyEditorSupport;

public class sendMessage {
    /**
     * 发送消息的基本方法：
     * void send(Message message) throws AmqpException;
     *
     * void send(String routingKey, Message message) throws AmqpException;
     *
     * void send(String exchange, String routingKey, Message message) throws AmqpException;
     *
     * void send(String routingKey, Message message, CorrelationData correlationData) throws AmqpException;
     *
     * void send(String exchange, String routingKey, Message message, CorrelationData correlationData) throws AmqpException;
     */
    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;

    private final ProduceMessage produceMessage;

    public sendMessage(ProduceCachingConnectionFactory produceCachingConnectionFactory, ProduceMessage produceMessage) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
        this.produceMessage = produceMessage;
    }

    /**
     * void send(String exchange, String routingKey, Message message) throws AmqpException;
     */
    public void sendMessage1(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.send("exchange","routingKey",produceMessage.createMessage());
    }

    /**
     * void send(String routingKey, Message message) throws AmqpException;
     * 发送大量的消息到同一个exchange,可以为template指定exchange
     */
    public void sendMessage2(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setExchange("exchange");
        rabbitTemplate.send("routingKey",produceMessage.createMessage());
    }

    /**
     * void send(Message message) throws AmqpException;
     * template指定了exchange和routingKey
     */
    public void sendMessage3(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setExchange("exchange");
        rabbitTemplate.setRoutingKey("routingKey");
        rabbitTemplate.send(produceMessage.createMessage());
    }

    /**
     * convertAndSend()方法会自动将字符串转换为 Message 对象，并将其发送到 RabbitMQ 服务器。
     */
    public void sendMessage4(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        Message message = produceMessage.createMessage();
        String messageStr = "test";
        rabbitTemplate.convertAndSend("exchange","routingKey",messageStr);
    }

}
