package com.yxh.miaosha.redis.key;

import com.yxh.miaosha.redis.BasePrefix;

/**
 * @author galaxy
 * @date 20-2-16 - 下午3:43
 */
public class OrderKey extends BasePrefix {

    public OrderKey(int expire,String prefix){
        super(expire,prefix);
    }

    public static OrderKey msOrder = new OrderKey(60*60,"mso");
}
