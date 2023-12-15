package com.rabbitmqtest.spingAMPQ.receiveMessage.sync;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class PollingMessage {

    /**
     * 消费者轮询
     */


    private final RabbitTemplate rabbitTemplate;

    public PollingMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Message receive() throws AmqpException;
     * Message receive(String queueName) throws AmqpException;
     * Message receive(long timeoutMillis) throws AmqpException;
     * Message receive(String queueName, long timeoutMillis) throws AmqpException;
     */
    public Message receiveMessage1(){
        rabbitTemplate.receive();
        String queueName = "test-queue-name";
        rabbitTemplate.receive(queueName);
        long timeOut = 10000L;
        rabbitTemplate.receive(timeOut);
        Message receivedMessage = rabbitTemplate.receive(queueName, timeOut);
        return receivedMessage;
    }
    /**
     * Object receiveAndConvert() throws AmqpException;
     * Object receiveAndConvert(String queueName) throws AmqpException;
     * Object receiveAndConvert(long timeoutMillis) throws AmqpException;
     * Object receiveAndConvert(String queueName, long timeoutMillis) throws AmqpException;
     */
    public Object receiveMessage2(){
        String queueName = "queueName";
        long timeOut = 1000L;
        Object received = rabbitTemplate.receiveAndConvert(queueName,timeOut);
        return received;
    }

    /**
     *<R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> callback)
     *        throws AmqpException;
     * <R, S> boolean receiveAndReply(String queueName, ReceiveAndReplyCallback<R, S> callback)
     *      throws AmqpException;
     * <R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> callback,
     *     String replyExchange, String replyRoutingKey) throws AmqpException;
     * <R, S> boolean receiveAndReply(String queueName, ReceiveAndReplyCallback<R, S> callback,
     *     String replyExchange, String replyRoutingKey) throws AmqpException;
     * <R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> callback,
     *      ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException;
     * <R, S> boolean receiveAndReply(String queueName, ReceiveAndReplyCallback<R, S> callback,
     *             ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException;
     */
    public Boolean receiveMessage3(){
        String queueName = "test-queue-name";
        //receiveAndReplyMessageCallback 不会进行消息的自动转换
        //receiveAndReplyCallback会进行消息的自动转换
        boolean reply = rabbitTemplate.receiveAndReply(queueName, new ReceiveAndReplyMessageCallback() {
            @Override
            public Message handle(Message message) {
                message.getMessageProperties().setHeader("测试","123321");
                return message;
            }
            //默认情况下，请求消息中的 replyTo 信息被用于路由回复
        }, new ReplyToAddressCallback<Message>() {
            @Override
            public Address getReplyToAddress(Message message, Message message2) {
                return new Address("exchangeName","routingKey");
            }
        });
        //receiveAndReplyCallback 可以返回 null。在这种情况下，没有回复被发送，receiveAndReply 的工作方式与 receive 方法类似。
        //ReceiveAndReplyCallback<Object, Object> 第一个参数为handle(Object o) 第二个为return 的Object
        rabbitTemplate.receiveAndReply(queueName, new ReceiveAndReplyCallback<Message, Object>() {
            @Override
            public Object handle(Message message) {
                String messageId = message.getMessageProperties().getMessageId();
                return messageId;
            }
            ////默认情况下，请求消息中的 replyTo 信息被用于路由回复
        }, new ReplyToAddressCallback<Object>() {
            @Override
            public Address getReplyToAddress(Message message, Object o) {
                return new Address("exchangeName","routingKey");
            }
        });
        return reply;
    }

}
