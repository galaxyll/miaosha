package com.yxh.miaosha.redis;

/**
 * @author galaxy
 * @date 20-2-10 - 下午5:16
 */
public class UserKey extends BasePrefix {

    public UserKey(String prefix) {
        super(prefix);
    }

    public UserKey( int seconds,String prefix) {
        super(seconds,prefix);
    }

    public static UserKey token = new UserKey(60*60*24,"tk");
}
