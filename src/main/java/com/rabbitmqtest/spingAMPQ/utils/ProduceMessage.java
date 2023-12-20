package com.rabbitmqtest.spingAMPQ.utils;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.CorrelationDataPostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import java.util.ArrayList;
import java.util.List;

public class ProduceMessage implements MessagePostProcessor , CorrelationDataPostProcessor{
    /**
     * message builder API 由 MessageBuilder 和 MessagePropertiesBuilder 提供
     * @return
     */
    public List<Message> createMessages(){
        ArrayList<Message> messages = new ArrayList<>();
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        for (int i = 0; i < 10; i++) {
            Message message = MessageBuilder.withBody(("testMessage" + i).getBytes())
                    .setMessageId("123")
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                    .setHeader("test", "测试")
                    .build();
            messages.add(message);
        }
        return messages;
    }

    public Message createMessage(){
        MessageProperties propertiesCopy = MessagePropertiesBuilder.newInstance().setMessageId("123").build();
        MessageProperties properties = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .copyProperties(propertiesCopy)
                .setHeader("test", "测试")
                .removeHeader("test")
                .build();
        Message message = MessageBuilder.withBody("testMessage".getBytes())
                .andProperties(properties)
                .build();
        return message;
    }

    /**
     * 对消息在发送到Rabbit服务器前进行处理
     */
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setHeader("myHeader","value");
        return message;
    }

    /**
     * 对消息在发送到Rabbit服务器前进行处理，并返回CorrelationData 用于在消息返回确认时使用
     */
    @Override
    public CorrelationData postProcess(Message message, CorrelationData correlationData) {
        message.getMessageProperties().setHeader("myHeader","value");
        return new CorrelationData("my-correlation-id");
    }
}
