package com.yihenchat;


import com.yihenchat.bean.Message;
import com.yihenchat.utils.MessageUtil;
import com.yihenchat.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTemplateTest {

    @Autowired
    private MessageUtil messageUtil;

    @Test
    void test1() {
        Message message = new Message();
        message.setText("出来打球");
        message.setFrom(3L);
        message.setTo(2L);

        Message message2 = new Message();
        message2.setText("几点");
        message2.setFrom(2L);
        message2.setTo(3L);

        Message message3 = new Message();
        message3.setText("下午3点");
        message3.setFrom(3L);
        message3.setTo(2L);

        messageUtil.saveMessage(message);
        messageUtil.saveMessage(message2);
        messageUtil.saveMessage(message3);

    }

    @Test
    void test2() {
        Message message = new Message();
        message.setText("null");
        message.setFrom(1L);
        message.setTo(2L);
        List<Object> message1 = messageUtil.getMessage(message);
        for (Object o : message1) {
            System.out.println(o.toString());
        }

    }
}
