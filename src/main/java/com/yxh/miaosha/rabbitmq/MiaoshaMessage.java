package com.yxh.miaosha.rabbitmq;

import com.yxh.miaosha.domain.User;

/**
 * @author galaxy
 * @date 20-2-16 - 下午7:40
 */
public class MiaoshaMessage {

    private User user;
    private long goodsId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
