package com.yxh.miaosha.util;

import java.util.UUID;

/**
 * @author galaxy
 * @date 20-2-11 - 下午3:18
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
