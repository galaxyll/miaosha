package com.yxh.miaosha.redis.key;

import com.yxh.miaosha.redis.BasePrefix;

/**
 * @author galaxy
 * @date 20-2-17 - 下午5:03
 */
public class MiaoshaKey extends BasePrefix {

    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey miaoshaPath = new MiaoshaKey(60,"msp");
}
