package com.yxh.miaosha.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author galaxy
 * @date 20-2-15 - 下午8:39
 */
@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";
    public static final String QUEUE1 = "queue1";
    public static final String QUEUE2 = "queue2";
    public static final String MIAOSHA_QUEUE = "miaoshaQueue";

    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE,true);
    }

    @Bean
    public Queue miaoshaQueue(){
        return new Queue(MIAOSHA_QUEUE,true);
    }

    @Bean
    public Queue queue1(){
        return new Queue(QUEUE1,true);
    }
    @Bean
    public Queue queue2(){
        return new Queue(QUEUE2,true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding bind1(){
        return  BindingBuilder.bind(queue1()).to(topicExchange()).with("topic.key1");
    }
    @Bean Binding bind2(){
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("topic.#");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding bind3(){
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }

    @Bean
    public Binding bind4(){
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }

}
