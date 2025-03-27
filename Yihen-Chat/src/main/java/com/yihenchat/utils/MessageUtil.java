package com.yihenchat.utils;


import com.yihenchat.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class MessageUtil {
    @Autowired
    private RedisUtil redisUtil;

    // 默认存储时间1天
    private final Long EXPIRETIME = 1L;

    // 存储聊天记录
    public void saveMessage(Message message) {
        Long from = message.getFrom(); // 发送者
        Long to = message.getTo(); // 接收者

        // 生成存储的key chat:message:id-id
        String key = "chat:message:" + Math.min(from, to) + "-" + Math.max(from, to);
        // 生成聊天记录
        String text = from + ":" + message.getText();

        // 储存聊天记录
        redisUtil.setListObjectWithRight(key, text);

        // 设置默认聊天记录存储时间
        redisUtil.setExpire(key, EXPIRETIME, TimeUnit.DAYS);

    }

    public List<Object> getMessage(Message message) {
        Long from = message.getFrom();
        Long to = message.getTo();

        // 生成存储的key chat:message:id-id
        String key = "chat:message:" + Math.min(from, to) + "-" + Math.max(from, to);
        List<Object> messages = redisUtil.getListAllObjects(key);
        return messages;
    }
}
