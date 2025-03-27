package com.yihenchat.utils;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Data
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    // 存储List对象，左侧添加元素
    public <T> void setListObjectWithLeft(String key, final T value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    // 存储List对象，右侧添加元素
    public <T> void setListObjectWithRight(String key, final T value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // 获取List对象所有数据 , 数据顺序 从左到右
    public <T> List<T> getListAllObjects(String key) {
        List<T> range = redisTemplate.opsForList().range(key, 0, -1);
        return range;
    }


    public boolean setExpire(String key , Long time , TimeUnit unit) {

        Boolean expire = redisTemplate.expire(key, time, unit);
        return expire;
    }


}
