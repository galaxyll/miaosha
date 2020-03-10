package com.yxh.miaosha.redis.key;

import com.yxh.miaosha.redis.BasePrefix;
import com.yxh.miaosha.redis.KeyPrefix;

import java.util.Random;

/**
 * @author galaxy
 * @date 20-2-13 - 下午3:30
 */
public class GoodsKey extends BasePrefix {

    private static Random random = new Random();

    public GoodsKey(int expire, String prefix) {
        super(expire,prefix);
    }

    public static GoodsKey goodsListKey = new GoodsKey(random.nextInt(60)%60+60,"gl");
    public static GoodsKey goodsDetailKey = new GoodsKey(60,"gd");
    public static GoodsKey msGoodsStock = new GoodsKey(0,"msgs");
    public static GoodsKey goodsIsOver = new GoodsKey(60*60*24,"gio");
}
