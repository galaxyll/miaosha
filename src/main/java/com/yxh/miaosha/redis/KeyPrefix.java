package com.yxh.miaosha.redis;

/**
 * @author galaxy
 * @date 20-2-10 - 下午4:55
 */
public interface KeyPrefix {
    public int getExpireSeconds();
    public String getPrefix();

}
