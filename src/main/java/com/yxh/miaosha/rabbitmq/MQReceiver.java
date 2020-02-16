package com.yxh.miaosha.rabbitmq;

import com.yxh.miaosha.domain.MiaoshaOrder;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.redis.RedisService;
import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import com.yxh.miaosha.service.GoodsService;
import com.yxh.miaosha.service.MiaoshaService;
import com.yxh.miaosha.service.OrderService;
import com.yxh.miaosha.vo.GoodsVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author galaxy
 * @date 20-2-15 - 下午8:39
 */
@Service
public class MQReceiver {

//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message){
//        System.out.println("receive msg:"+message);
//    }
//
//    @RabbitListener(queues = MQConfig.QUEUE1)
//    public void receive1(String message){
//        System.out.println("receive1 msg:"+message);
//    }
//
//    @RabbitListener(queues = MQConfig.QUEUE2)
//    public void receive2(String message){
//        System.out.println("receive2 msg:"+message);
//    }
//    @RabbitListener(queues = MQConfig.QUEUE1)
//    public void receive3(String message){
//        System.out.println("receive1 msg:"+message);
//    }
//    @RabbitListener(queues = MQConfig.QUEUE2)
//    public void receive4(String message){
//        System.out.println("receive2 msg:"+message);
//    }

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void miaoshaMessageReceiver(String message){
        MiaoshaMessage msg = RedisService.stringToBean(message,MiaoshaMessage.class);
        User user = msg.getUser();
        long goodsId = msg.getGoodsId();

        /*
          查询重复购买
         */
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUGId(user.getId(),goodsId);
        if (miaoshaOrder!=null){
            return ;
        }

        GoodsVo goodsVo = goodsService.getGoodsVoById(goodsId);
        miaoshaService.miaosha(user,goodsVo);
    }
}
