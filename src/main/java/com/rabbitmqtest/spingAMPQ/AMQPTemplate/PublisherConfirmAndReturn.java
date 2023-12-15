package com.rabbitmqtest.spingAMPQ.AMQPTemplate;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import com.rabbitmqtest.spingAMPQ.utils.ProduceMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class PublisherConfirmAndReturn {
    /**
     * AmqpTemplate 的 RabbitTemplate 实现支持发布者确认和返回。
     * 对于返回的消息，template 的 mandatory 属性必须被设置为 true，或者对于特定的消息，mandatory-expression 必须评估为 true。
     * 此功能要求 CachingConnectionFactory 将其 publisherReturns 属性设置为 true（请参阅 发布者消息确认和返回）。
     * 通过调用 setReturnsCallback(ReturnsCallback callback) 注册
     */
    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;

    private final ProduceMessage produceMessage;

    public PublisherConfirmAndReturn(ProduceCachingConnectionFactory produceCachingConnectionFactory, ProduceMessage produceMessage) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
        this.produceMessage = produceMessage;
    }

    private static void confirm(CorrelationData correlationData, boolean b, String s) {
        correlationData.setId("1");
    }

    public CachingConnectionFactory makeConfirmAndReturn(){
        CachingConnectionFactory factory = produceCachingConnectionFactory.createCCF();
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        factory.setPublisherReturns(true);
        return factory;
    }

    /**
     * 返回回调
     * @return
     */
    public AmqpTemplate doReturn(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                Message message = returnedMessage.getMessage();
                int replyCode = returnedMessage.getReplyCode();
                String exchange = returnedMessage.getExchange();
                String replyText = returnedMessage.getReplyText();
                String routingKey = returnedMessage.getRoutingKey();
            }
        });
        //lambda表达式：
        rabbitTemplate.setReturnsCallback((returnedMessage)->{
            Message message = returnedMessage.getMessage();
            int replyCode = returnedMessage.getReplyCode();
            String exchange = returnedMessage.getExchange();
            String replyText = returnedMessage.getReplyText();
            String routingKey = returnedMessage.getRoutingKey();
        });
        return rabbitTemplate;
    }

    public AmqpTemplate doConfirm(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {

            }
        });
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause)->{
            if (ack){
                System.out.println("message with correlationData"+
                        correlationData.getId()+" was successfully confirmed");
            } else {
                System.out.println("message with correlationData"+
                        correlationData.getId()+"was NOT confirmed:"+cause);
            }
        });
        return rabbitTemplate;
    }

    public void testCorrelationData(){
        CorrelationData correlationData = new CorrelationData("testId");
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        Message message = produceMessage.createMessage();
        rabbitTemplate.convertAndSend("exchange","routingKey",message,correlationData);
    }
}
