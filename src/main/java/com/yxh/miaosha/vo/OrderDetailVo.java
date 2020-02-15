package com.yxh.miaosha.vo;

import com.yxh.miaosha.domain.OrderInfo;
import com.yxh.miaosha.domain.User;

/**
 * @author galaxy
 * @date 20-2-15 - 下午12:07
 */
public class OrderDetailVo {
    private User user;
    private GoodsVo goods;
    private OrderInfo orderInfo;

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}
