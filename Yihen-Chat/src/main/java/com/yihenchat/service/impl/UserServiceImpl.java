package com.yihenchat.service.impl;

import com.yihenchat.bean.User;
import com.yihenchat.dto.Result;
import com.yihenchat.mapper.UserMapper;
import com.yihenchat.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public Result getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        user.setPassword(null);
        return Result.ok(null,user);
    }
}
