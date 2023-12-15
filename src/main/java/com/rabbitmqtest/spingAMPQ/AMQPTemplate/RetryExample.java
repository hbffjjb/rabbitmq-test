package com.rabbitmqtest.spingAMPQ.AMQPTemplate;

import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class RetryExample {
    private final RetryTemplate retryTemplate;

    public RetryExample(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    public void doSomething(){
        retryTemplate.execute((retryContext)->{
            try {
                this.mightFailOperation();
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void mightFailOperation(){
        //可能会出错的业务代码
    }

}
