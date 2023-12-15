package com.rabbitmqtest.spingAMPQ.AMQPTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {
    /**
     * 从 1.3 版开始，你现在可以将 RabbitTemplate 配置为使用 RetryTemplate 以帮助处理broker连接问题。
     * 请参阅 spring-retry 项目以了解完整信息。
     * 下面只是一个使用指数回退策略和默认 SimpleRetryPolicy 的示例，它在向调用者抛出异常之前进行了三次尝试。
     * @return
     */

    @Bean
    public RetryTemplate retryTemplate(){
        RetryTemplate retryTemplate = new RetryTemplate();
        //设置重试策略
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(5);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        //设置回退策略
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(1000L);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        return retryTemplate;
    }
}
