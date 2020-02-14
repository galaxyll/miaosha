package com.yxh.miaosha.vo;

import com.yxh.miaosha.domain.User;

/**
 * @author galaxy
 * @date 20-2-14 - 下午9:51
 */
public class GoodsDetailVo {

    private int miaoshaStatus ;
    private int remianSeconds ;
    private GoodsVo goods;
    private User user;

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemianSeconds() {
        return remianSeconds;
    }

    public void setRemianSeconds(int remianSeconds) {
        this.remianSeconds = remianSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
