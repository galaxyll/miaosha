package com.yxh.miaosha.rabbitmq;

import com.yxh.miaosha.redis.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author galaxy
 * @date 20-2-15 - 下午8:38
 */
@Service
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

//    public void send(Object message){
//        String msg = RedisService.beanToString(message);
//        System.out.println("send msg:"+msg);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);
//    }
//    public void send1(Object message){
//        String msg = RedisService.beanToString(message);
//        System.out.println("send msg:"+msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key1",msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key2",msg);
//    }
//    public void send2(Object message){
//        String msg = RedisService.beanToString(message);
//        System.out.println("send msg:"+msg);
//        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"",msg);
//    }

    public void sendMiaoshaMessage(MiaoshaMessage message){
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,msg);
    }
}
