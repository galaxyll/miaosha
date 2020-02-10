package com.yxh.miaosha.redis;

/**
 * @author galaxy
 * @date 20-2-10 - 下午5:16
 */
public class UserKey extends BasePrefix {

    public UserKey( String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");

}
