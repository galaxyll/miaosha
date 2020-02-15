package com.yxh.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author galaxy
 * @date 20-2-10 - 下午1:47
 */
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix keyPrefix,String key,Class<T> tClass){

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            String reallyKey = keyPrefix.getPrefix()+key;
            T t = stringToBean(jedis.get(reallyKey),tClass);
            return t;
        }finally {
            returnJedisToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix keyPrefix,String key,T value){

        Jedis jedis =  null;

        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str==null||str.length()<=0){
                return false;
            }
            String reallyKey = keyPrefix.getPrefix()+key;
            int seconds = keyPrefix.getExpireSeconds();
            if (seconds<=0){
                jedis.set(reallyKey,str);
            }else {
                jedis.setex(reallyKey,seconds,str);
            }
            return true;
        }finally {
            returnJedisToPool(jedis);
        }
    }

    public <T> boolean exists(KeyPrefix keyPrefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String reallyKey = keyPrefix.getPrefix()+key;
            return jedis.exists(reallyKey);
        }finally {
            returnJedisToPool(jedis);
        }
    }
    /**
     *fastJSON不能解析Integer、int、String等
     *
     * @param <T> 指定对象类型
     * @param str 待转换的字符串
     * @param tClass 指定对象类型实例
     * @return 指定类型对象
     */
    public static <T> T stringToBean(String str, Class<T> tClass) {
        if (str==null||str.length()<=0||tClass==null){
            return null;
        }
        if (tClass==Integer.class || tClass==int.class){
            return (T) Integer.valueOf(str);
        }
        if (tClass==Long.class||tClass==long.class){
            return (T) Long.valueOf(str);
        }
        if (tClass==String.class){
            return (T) str;
        }

        return JSON.toJavaObject(JSON.parseObject(str),tClass);
    }
    public static <T> String beanToString(T value) {
        if (value==null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz==Integer.class||clazz==int.class){
            return ""+value;
        }
        if (clazz==Long.class||clazz==long.class){
            return ""+value;
        }
        if (clazz==String.class){
            return (String) value;
        }
        return JSON.toJSONString(value);
    }

    /**
     * 删除指定键值对
     * @param prefix prefix
     * @param key key
     * @return boolean
     */
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();

            String realKey  = prefix.getPrefix() + key;
            long ret =  jedis.del(realKey);
            return ret > 0;
        }finally {
            returnJedisToPool(jedis);
        }
    }

    /**
     * 将连接池返回的jedis回收，当有datasource时，.close()方法不是关闭而是回收
     *
     * @param jedis
     */
    private void returnJedisToPool(Jedis jedis) {
        if (jedis!=null){
            jedis.close();
        }
    }

}
