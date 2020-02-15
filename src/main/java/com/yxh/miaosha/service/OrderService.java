package com.yxh.miaosha.service;

import com.yxh.miaosha.dao.OrderDao;
import com.yxh.miaosha.domain.MiaoshaOrder;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
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

    public MiaoshaOrder getMiaoshaOrderByUGId(Long userId, Long goodsId) {
        return orderDao.getMiaoshaOrderByUGId(userId,goodsId);
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
        long orderId = orderDao.insertOrder(order);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setOrderId(orderId);
        orderDao.insertMiaoshaOrder(miaoshaOrder);

        return order;
    }

    public OrderInfo getOrderInfoById(long orderId) {
        return orderDao.getOrderInfoById(orderId);
    }
}
