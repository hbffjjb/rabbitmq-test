package com.rabbitmqtest.spingAMPQ.MessageConverters;

import com.rabbitmqtest.spingAMPQ.pojo.Cat;
import com.rabbitmqtest.spingAMPQ.pojo.Hat;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class Jackson2JSONMCTest {
    /**
     * 配置Jack2JsonMessageConverter
     * Jackson2JsonMessageConverter默认使用一个 DefaultClassMapper.
     * 类型信息被添加到（并从）MessageProperties 中检索。
     * 如果一个入站的消息在 MessageProperties 中不包含类型信息，但你知道预期的类型，
     * 你可以通过使用 defaultType 属性配置一个静态类型，如下例所示：
     */

    @Bean
    public DefaultClassMapper classMapper(){
        DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
        HashMap<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("cat", Cat.class);
        idClassMapping.put("Hat", Hat.class);
        defaultClassMapper.setIdClassMapping(idClassMapping);
        return defaultClassMapper;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.setClassMapper(classMapper());
        return jsonMessageConverter;
    }


}


