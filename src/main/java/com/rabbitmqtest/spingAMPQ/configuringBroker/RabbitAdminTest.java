package com.rabbitmqtest.spingAMPQ.configuringBroker;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;

public class RabbitAdminTest {

    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;
    public RabbitAdminTest(ProduceCachingConnectionFactory produceCachingConnectionFactory) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }

    @Bean("rabbitAdmin")
    public RabbitAdmin createRabbitAdmin(){
        return new RabbitAdmin(produceCachingConnectionFactory.createCCF());
    }

    @Bean
    public void initializeRabbit(@Qualifier("rabbitAdmin") RabbitAdmin rabbitAdmin){
        DirectExchange direct = new DirectExchange("direct");
        FanoutExchange fanout = new FanoutExchange("fanout");
        HeadersExchange headers = new HeadersExchange("headers");
        TopicExchange topic = new TopicExchange("topic");
        //声明交换机
        rabbitAdmin.declareExchange(fanout);
        //声明队列
        Queue queue = rabbitAdmin.declareQueue();
        rabbitAdmin.declareQueue(new Queue("test-queue"));
        //声明绑定关系
        Binding binding = BindingBuilder.bind(queue).to(fanout);
        rabbitAdmin.declareBinding(binding);

        rabbitAdmin.deleteExchange("test-delete-exchange");
        rabbitAdmin.deleteQueue("test-delete-queue");
        rabbitAdmin.removeBinding(binding);

    }

}
