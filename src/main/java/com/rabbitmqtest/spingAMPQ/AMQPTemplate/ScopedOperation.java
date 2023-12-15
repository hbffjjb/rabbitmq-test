package com.rabbitmqtest.spingAMPQ.AMQPTemplate;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import com.rabbitmqtest.spingAMPQ.utils.ProduceMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

public class ScopedOperation {

    /**
     * 通常情况下使用的template时，channel会从缓存中检查或创建出来，操作完后返回到缓存中重新使用。
     * 在一个多线程的环境中，不能保证下一个操作使用同一个 channel。
     * 然而，有时你可能想对 channel 的使用有更多的控制，并确保一些操作都是在同一个 channel 上进行的。
     * 从2.0开始，提供了一个名为invoke的新方法，该方法有一个OperationCallback。
     * 在回调scope内和在所提供的RabbitOperations参数上执行的任何操作都使用相同的专用Channel,它将在结束时被关闭(不返回到缓存)。
     * 如果该channel是PublisherCallbackChannel，它将在收到所有确认后返回到缓存中。
     */

    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;

    private final ProduceMessage produceMessage;
    public ScopedOperation(ProduceCachingConnectionFactory produceCachingConnectionFactory, ProduceMessage produceMessage) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
        this.produceMessage = produceMessage;
    }

    public void test(){
        CachingConnectionFactory factory = produceCachingConnectionFactory.createCCF();
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.invoke(new RabbitOperations.OperationsCallback<Object>() {
//            @Override
//            public Object doInRabbit(RabbitOperations rabbitOperations) {
//                return true;
//            }
//        });
        List<Message> messages = produceMessage.createMessages();
        Boolean result = rabbitTemplate.invoke(t -> {
            messages.forEach(m -> t.convertAndSend("routingKey", m));
            t.waitForConfirmsOrDie(10_000);
            return true;
        });
    }

}
