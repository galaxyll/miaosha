package com.yxh.miaosha.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yxh.miaosha.domain.MiaoshaOrder;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.redis.RedisService;
import com.yxh.miaosha.redis.key.GoodsKey;
import com.yxh.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author galaxy
 * @date 20-2-12 - 下午12:05
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(User user, GoodsVo goodsVo){

        Boolean success = goodsService.reduceStock(goodsVo);
        if (success){
            return orderService.createOrder(user,goodsVo);
        }else {
            setGoodsOver(goodsVo.getId());
            return null;
        }

    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(GoodsKey.goodsIsOver,goodsId.toString(),true);
    }

    private Boolean getGoodsOver(long goodsId) {
        return redisService.exists(GoodsKey.goodsIsOver,""+goodsId);
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUGId(userId,goodsId);
        if (order!=null){
            return order.getOrderId();
        }
        Boolean isOver = getGoodsOver(goodsId);
        if (isOver){
            return -1;
        }else {
            return 0;
        }
    }

}
