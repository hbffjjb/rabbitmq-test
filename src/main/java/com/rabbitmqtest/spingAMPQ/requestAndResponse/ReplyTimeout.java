package com.rabbitmqtest.spingAMPQ.requestAndResponse;

import com.rabbitmqtest.spingAMPQ.utils.ProduceMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ReplyTimeout {

    private final ProduceMessage produceMessage;

    public ReplyTimeout(ProduceMessage produceMessage) {
        this.produceMessage = produceMessage;
    }

    public RabbitTemplate TimeoutTemplate(){
        RabbitTemplate template = new RabbitTemplate();
        //设置回复的超时时间
        template.setReplyTimeout(6000L);
        //确保消息可以得到回复
        //如果消息没有队列接收，会返回AmqpMessageReturnedException
        template.setMandatory(true);

        //returned message 是发布者发送后，未被队列接收的消息，确保消息不会丢失
        template.setReturnsCallback((returnedMessage)->{
            System.out.println(returnedMessage.getMessage().getBody().toString());
        });
        template.setConfirmCallback((correlationData,ack,cause)->{
            if (ack){
                System.out.println("消息已被确认");
            }else {
                System.out.println("消息未被确认，cause："+cause);
            }
        });

        //为每次回复都设置一个临时、独占的队列
        template.setUseTemporaryReplyQueues(true);
        //为回复创建监听容器，不必为每次请求都创建新的消费者
        template.setUseDirectReplyToContainer(true);


        return template;
    }

}
