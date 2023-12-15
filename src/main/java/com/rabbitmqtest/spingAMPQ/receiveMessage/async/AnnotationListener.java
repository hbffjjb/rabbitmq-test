package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;

public class AnnotationListener {

    /**
     * 只要名为 myQueue 的队列上有一个消息，processOrder 方法就会被相应地调用
     */
    @RabbitListener(queues = {"myQueue1","myQueue2"})
    @SendTo()
    public void processOrder(String data){

    }

    /**
     * 如果需要的话，一个队列 myQueue 和exchange一起被自动声明（durable），并通过 routing key 绑定到 exchange
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "myQueue",durable = "false"),
            exchange = @Exchange(value = "test-exchange",ignoreDeclarationExceptions = "true"),
            key = "routingKey"))
    public void processOrder2(String data, @Header(AmqpHeaders.CONSUMER_QUEUE) String Queue){
        System.out.println(data);
    }

    /**
     * 也可以在 @QueueBinding 注解中为 queue、exchange 和 binding 指定参数
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "myQueue",durable = "false",
                                            arguments = @Argument(name = "x-message-ttl",value = "10000",type = "java.lang.Integer")),
            exchange = @Exchange(value = "test-exchange",ignoreDeclarationExceptions = "true"
                                ,arguments = {@Argument(name = "x-match",value = "all")
                                            ,@Argument(name = "arg1",value = "value1")
                                            ,@Argument(name = "arg2",value = "value2")}),
            key = "routingKey"))
    public void processOrder3(String data){

    }


}
