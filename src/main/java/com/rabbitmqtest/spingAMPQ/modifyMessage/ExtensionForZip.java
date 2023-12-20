package com.rabbitmqtest.spingAMPQ.modifyMessage;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.postprocessor.*;
import org.springframework.context.annotation.Bean;

public class ExtensionForZip {
    /**
     * 对mq接收到的消息进行压缩和解压缩
     * @return
     */
    @Bean
    public RabbitTemplate createTemplateWithExtension(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        //gzip and gunzip
        rabbitTemplate.setBeforePublishPostProcessors(new GZipPostProcessor());
        rabbitTemplate.setAfterReceivePostProcessors(new GUnzipPostProcessor());

        //zip and unzip
        rabbitTemplate.setBeforePublishPostProcessors(new ZipPostProcessor());
        rabbitTemplate.setAfterReceivePostProcessors(new UnzipPostProcessor());

        //deflater and inflater
        rabbitTemplate.setBeforePublishPostProcessors(new DeflaterPostProcessor());
        rabbitTemplate.setAfterReceivePostProcessors(new InflaterPostProcessor());

        //允许将新的后置处理器分别追加到发布前和接收后处理器的列表中
        rabbitTemplate.addBeforePublishPostProcessors(new ZipPostProcessor());
        rabbitTemplate.addAfterReceivePostProcessors(new ZipPostProcessor());
        //删除后置处理器
        rabbitTemplate.removeAfterReceivePostProcessor(new ZipPostProcessor());
        rabbitTemplate.removeBeforePublishPostProcessor(new ZipPostProcessor());

        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer createContainerWithExtension(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        //解压在容器收到消息后进行。
        container.setAfterReceivePostProcessors();
        return null;
    }


}
