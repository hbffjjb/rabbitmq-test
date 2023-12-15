package com.rabbitmqtest.spingAMPQ.sendMessage;

import com.rabbitmqtest.spingAMPQ.utils.ProduceMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;

public class BatchSend {
    private final ProduceMessage produceMessage;

    private final BatchingRabbitTemplate batchingRabbitTemplate;
    public BatchSend(ProduceMessage produceMessage, BatchingRabbitTemplate batchingRabbitTemplate) {
        this.produceMessage = produceMessage;
        this.batchingRabbitTemplate = batchingRabbitTemplate;
    }

    public void batchSend(){
        long time1 = System.currentTimeMillis();
        Message message = produceMessage.createMessage();
        String messageStr = Arrays.toString(message.getBody());
        for (int i = 0; i < 10; i++) {
            String msgStr = "batch-"+messageStr+i;
            Message msg = new Message(msgStr.getBytes());
            batchingRabbitTemplate.send("queueName",msg);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("批量消息发送完毕，用时:"+(time2-time1));
    }
}
