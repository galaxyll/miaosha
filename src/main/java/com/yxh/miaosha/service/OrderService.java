package com.yxh.miaosha.service;

import com.yxh.miaosha.dao.OrderDao;
import com.yxh.miaosha.domain.MiaoshaOrder;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
import com.yxh.miaosha.redis.RedisService;
import com.yxh.miaosha.redis.key.OrderKey;
import com.yxh.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author galaxy
 * @date 20-2-12 - 上午11:55
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUGId(Long userId, Long goodsId) {

        MiaoshaOrder miaoshaOrder = redisService.get(OrderKey.msOrder,""+userId+goodsId,MiaoshaOrder.class);
        if (miaoshaOrder==null){
            miaoshaOrder = orderDao.getMiaoshaOrderByUGId(userId,goodsId);
        }
        if (miaoshaOrder==null){
            return null;
        }
        redisService.set(OrderKey.msOrder,""+userId+goodsId,miaoshaOrder);
        return miaoshaOrder;
    }

    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goodsVo) {

        OrderInfo order = new OrderInfo();
        order.setGoodsId(goodsVo.getId());
        order.setCreateDate(new Date());
        order.setDeliveryAddrId(0L);
        order.setGoodsCount(1);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsPrice(goodsVo.getMiaoshaPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setUserId(user.getId());
        orderDao.insertOrder(order);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setOrderId(order.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);

        return order;
    }

    public OrderInfo getOrderInfoById(long orderId) {
        return orderDao.getOrderInfoById(orderId);
    }
}
