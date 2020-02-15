package com.yxh.miaosha.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author galaxy
 * @date 20-2-15 - 下午8:39
 */
@Service
public class MQReceiver {

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        System.out.println("receive msg:"+message);
    }

    @RabbitListener(queues = MQConfig.QUEUE1)
    public void receive1(String message){
        System.out.println("receive1 msg:"+message);
    }

    @RabbitListener(queues = MQConfig.QUEUE2)
    public void receive2(String message){
        System.out.println("receive2 msg:"+message);
    }
    @RabbitListener(queues = MQConfig.QUEUE1)
    public void receive3(String message){
        System.out.println("receive1 msg:"+message);
    }
    @RabbitListener(queues = MQConfig.QUEUE2)
    public void receive4(String message){
        System.out.println("receive2 msg:"+message);
    }
}
