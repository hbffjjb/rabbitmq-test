package com.rabbitmqtest.spingAMPQ.sendMessage;

import com.rabbitmqtest.spingAMPQ.utils.ProduceCachingConnectionFactory;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class BatchConfig {

    private final ProduceCachingConnectionFactory produceCachingConnectionFactory;
    public BatchConfig(ProduceCachingConnectionFactory produceCachingConnectionFactory) {
        this.produceCachingConnectionFactory = produceCachingConnectionFactory;
    }

    @Bean("batchQueueTaskScheduler")
    public TaskScheduler batchQueueTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }

    //!!!重点： 所谓批量， 就是spring 将多条message重新组成一条message, 发送到mq, 从mq接受到这条message后，在重新解析成多条message
    @Bean
    public BatchingRabbitTemplate batchingRabbitTemplate(@Qualifier("batchQueueTaskScheduler") TaskScheduler taskScheduler){
        //一次批量的数量
        int batchSize = 10;
        //缓存大小限制，单位字节
        //simpleBatchingStrategy策略，是判断message数量是否超过了batchSize或者message的大小是否超过缓存限制
        //缓存限制，主要用于限制组装后的一条消息的大小
        //如果主要通过数量来做批量，缓存设置大些
        int bufferLimit = 1024;     // 1K
        long timeout = 10000;

        //该策略只支持一个exchange和routingKey
        SimpleBatchingStrategy simpleBatchingStrategy = new SimpleBatchingStrategy(batchSize, bufferLimit, timeout);
        return new BatchingRabbitTemplate(produceCachingConnectionFactory.createCCF(), simpleBatchingStrategy, taskScheduler);
    }


}
