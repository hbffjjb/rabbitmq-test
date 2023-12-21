package com.rabbitmqtest.spingAMPQ.requestAndResponse;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import com.rabbitmqtest.spingAMPQ.utils.ProduceMessage;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutionException;

public class AsyncRabbitTemplateTest {

    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;
    private final ProduceMessage produceMessage;
    public AsyncRabbitTemplateTest(ProduceCachingConnectionFactory produceCachingConnectionFactory
                            , ProduceMessage produceMessage) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
        this.produceMessage = produceMessage;
    }

    @Bean("asyncRabbitTemplate")
    public AsyncRabbitTemplate createAsyncRabbitTemplate(){
        return new AsyncRabbitTemplate(produceCachingConnectionFactory.createCCF(),"exchange","routingKey");
    }

    public void testAsyncRabbitTemplate(@Qualifier("asyncRabbitTemplate")AsyncRabbitTemplate asyncRabbitTemplate) {
        AsyncRabbitTemplate.RabbitConverterFuture<String> future =
                asyncRabbitTemplate.convertSendAndReceive(produceMessage.createMessage());
        try {
            String reply = future.get();
            System.out.println("send message succeed,receive reply："+reply);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}
