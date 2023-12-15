package com.rabbitmqtest.spingAMPQ.receiveMessage.async;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 对多个监听器（listener）使用相同的配置。为了减少模板配置，可以使用元注解来创建的监听器注解
 * 由 @MyAnonFanoutListener 注解创建的每个监听器都将一个匿名的、自动删除的队列绑定到 fanout exchange，metaFanout
 */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RabbitListener(bindings = @QueueBinding(
        value = @Queue,
        exchange = @Exchange(value = "metaDirectExchange",type = ExchangeTypes.DIRECT)))
public @interface MetaAnnotationListener {

}
