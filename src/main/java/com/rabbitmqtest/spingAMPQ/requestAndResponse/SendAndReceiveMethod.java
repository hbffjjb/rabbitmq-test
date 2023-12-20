package com.rabbitmqtest.spingAMPQ.requestAndResponse;

import com.rabbitmq.client.AMQP;
import com.rabbitmqtest.spingAMPQ.pojo.Cat;
import com.rabbitmqtest.spingAMPQ.pojo.Hat;
import com.rabbitmqtest.spingAMPQ.utils.ProduceMessage;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Arrays;

public class SendAndReceiveMethod {

    private final RabbitTemplate template;
    private final ProduceMessage produceMessage;
    public SendAndReceiveMethod(RabbitTemplate template, ProduceMessage produceMessage) {
        this.template = template;
        this.produceMessage = produceMessage;
    }

    @Bean
    public RabbitTemplate templateTest(){
        //发送消息并接收到response
        Message responseMsg1 = template.sendAndReceive(produceMessage.createMessage());
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("111");
        Message responseMsg2 = template.sendAndReceive("exchange", "routingKey",
                produceMessage.createMessage(),correlationData);
        //发送Cat类型的消息，并收到发布者的返回消息，设置该返回消息的类型为Hat类
        Hat responseHat = template.convertSendAndReceiveAsType("exchange", "routingKey",
                new Cat(), ParameterizedTypeReference.forType(Hat.class));
        if (responseMsg1 != null){
            System.out.println("received response："+ Arrays.toString(responseMsg1.getBody()));
        }
        return template;

    }




}
