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

    public <T> T get(String key,Class<T> tClass){

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            T t = stringToBean(jedis.get(key),tClass);
            return t;
        }finally {
            returnJedisToPool(jedis);
        }
    }

    public <T> boolean set(String key,T value){

        Jedis jedis =  null;

        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str==null||str.length()<=0){
                return false;
            }
            jedis.set(key,str);
            return true;
        }finally {
            returnJedisToPool(jedis);
        }
    }

    /**
     *
     * @param str 待转换的字符串
     * @param tClass 指定对象类型实例
     * @param <T> 指定对象类型
     * @return 指定类型对象
     */
    private <T> T stringToBean(String str,Class<T> tClass) {
        if (str==null||str.length()<=0||tClass==null){
            return null;
        }

        return JSON.toJavaObject(JSON.parseObject(str),tClass);
    }

    private <T> String beanToString(T value) {
        if (value==null){
            return null;
        }

        return JSON.toJSONString(value);
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
