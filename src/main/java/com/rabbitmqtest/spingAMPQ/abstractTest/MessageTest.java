package com.rabbitmqtest.spingAMPQ.abstractTest;

import com.alibaba.fastjson.JSON;
import com.rabbitmqtest.spingAMPQ.pojo.Body;
import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

public class MessageTest {

    @Bean
    public Message createBasicMessage(String msg){
        Message message = MessageBuilder.withBody(msg.getBytes()).build();
        message.getMessageProperties().setMessageId("111");
        message.getMessageProperties().setExpiration("1000");       //设置过期时间
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);     //设置持久化
        return message;
    }

    @Bean
    public Message createBodyMessage(String msg){
        Body body = new Body();
        body.setMessage(msg);
        byte[] bytes = JSON.toJSONString(body).getBytes();
        Message message = MessageBuilder.withBody(bytes).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        return message;
    }

}
