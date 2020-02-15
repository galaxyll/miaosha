package com.yxh.miaosha.service;

import com.yxh.miaosha.domain.Goods;
import com.yxh.miaosha.domain.MiaoshaGoods;
import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;
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

    @Transactional
    public OrderInfo miaosha(User user, GoodsVo goodsVo){

        goodsService.reduceStock(goodsVo);
        OrderInfo order = orderService.createOrder(user,goodsVo);
        return order;
    }
}
