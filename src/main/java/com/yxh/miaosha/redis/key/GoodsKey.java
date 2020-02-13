package com.yxh.miaosha.redis.key;

import com.yxh.miaosha.redis.BasePrefix;

/**
 * @author galaxy
 * @date 20-2-13 - 下午3:30
 */
public class GoodsKey extends BasePrefix {


    public GoodsKey(int expire, String prefix) {
        super(expire,prefix);
    }

    public static GoodsKey goodsListKey = new GoodsKey(60,"gl");
    public static GoodsKey goodsDetailKey = new GoodsKey(60,"gd");
}
