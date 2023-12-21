package com.rabbitmqtest.spingAMPQ.configuringBroker;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import java.util.Collections;
import java.util.List;

@Configuration
public class DeclarableTest {

    private static final String prototypeQueueName = "prototypeQueue";
    @Bean("cf")
    public CachingConnectionFactory cf(){
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public RabbitAdmin ra(@Qualifier("cf") CachingConnectionFactory factory){
        return new RabbitAdmin(factory);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct-test",true,false, Collections.singletonMap("key1","val1"));
    }

    @Bean
    public Queue queue1(){
        return new Queue("q1",false,false,false);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue1()).to(directExchange()).with("routingKey1");
    }

    @Bean
    public Declarables declarableExchange(){
        return new Declarables(
                new FanoutExchange("fanout-exchange",false,false),
                new TopicExchange("topic-exchange",false,false)
        );
    }

    @Bean
    public Declarables declarableQueue(){
        return new Declarables(
                new Queue("q2",false,false,false),
                new Queue("q3",false,false,false)
        );
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) //作用域为prototype,每次请求该Bean时，都会创建一个新的Bean
    public Declarables prototype(){
        return new Declarables(new Queue(prototypeQueueName,false,false,false));
    }

    @Bean
    public Declarables declarableBinding(){
        return new Declarables(
                new Binding("q2", Binding.DestinationType.QUEUE,"fanout-exchange","k2",null),
                new Binding("q3", Binding.DestinationType.QUEUE,"topic-exchange","k3",null),
                BindingBuilder.bind(queue1()).to(directExchange()).with("k1")
        );
    }

    @Bean
    public Declarables declarables() {
        return new Declarables(
                new DirectExchange("dir", false, false, null),
                new Queue("q4", false, false, false),
                new Binding("q4", Binding.DestinationType.QUEUE, "dir", "k4", null)
        );
    }

    @Bean
    public SimpleMessageListenerContainer container(Declarables declarables){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(cf());
        container.setQueues(declarables.getDeclarablesByType(Queue.class).toArray(new Queue[0]));
        container.setMessageListener((message)->{
            System.out.println("received message："+message.getBody().toString());
        });
        return container;
    }


}
