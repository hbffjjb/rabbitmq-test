package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import com.rabbitmqtest.spingAMPQ.pojo.Cat;
import com.rabbitmqtest.spingAMPQ.pojo.Hat;
import com.rabbitmqtest.spingAMPQ.pojo.Things;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@RabbitListener(id = "multi",queues = "multiMethodQueue")
public class MultiMethodListener {

    /**
     * @Payload 注解通常与 @RabbitListener 注解一起使用，用于接收 RabbitMQ 消息中的有效负载（payload）。
     * 如果multiMethodQueue队列传过来的Message是Things类型，调用第一种方法
     * Hat类型调用第二种方法，Cat类型调用第三种。
     * 如果不是上述的三种类型，调用默认方法
     */
    @RabbitHandler
    public String things(@Payload Things things){
        System.out.println("received things message");
        return things.getTxt();
    }

    @RabbitHandler
    public String Hat(@Payload Hat hat){
        System.out.println("received hat message");
        return hat.getColor();
    }

    @RabbitHandler
    public String Cat(@Header("correlationId")String correlationId, @Payload Cat cat){    //@Header用于接收消息中的头信息
        System.out.println("received cat message,correlationId:"+correlationId);
        return cat.getColor();
    }

    @RabbitHandler(isDefault = true)
    public String defaultMethod(){
        System.out.println("received default message");
        return null;
    }

}
