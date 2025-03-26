package com.yihenchat;


import com.yihenchat.bean.User;
import com.yihenchat.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyBatisPlusTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void test1() {
        User user = userMapper.selectById(1L);
        System.out.println(user.getUserId());
    }

    @Test
    void test2() {
        String s = UUID.randomUUID().toString();
        System.out.println(s);
    }
}
