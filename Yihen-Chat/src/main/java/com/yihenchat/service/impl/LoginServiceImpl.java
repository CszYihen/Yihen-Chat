package com.yihenchat.service.impl;

import com.yihenchat.bean.OnlineUser;
import com.yihenchat.bean.User;
import com.yihenchat.dto.Result;
import com.yihenchat.mapper.UserMapper;
import com.yihenchat.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserMapper userMapper;

    // 存放在线用户的昵称头像
    public static ConcurrentHashMap<Long, OnlineUser> onlineUsersPools = new ConcurrentHashMap<>();
    @Override
    public Result login(Long userId, String password) {
        User user = userMapper.selectById(userId);
        if (Objects.isNull(user)) {
            return Result.error("用户不存在！");
        }
        if (!password.equals(user.getPassword())) {
            return Result.error("用户id错误或密码错误");
        }
        // 保存在线用户
        saveOnlineUser(user);
        user.setPassword(UUID.randomUUID().toString());
        return Result.ok("登陆成功!",user);
    }

    @Override
    public Result register(Long userId, String password) {
        User user = userMapper.selectById(userId);
        if (!Objects.isNull(user)) {
            return Result.error("用户已存在！");
        }
        User user_ = createUser(userId, password);
        userMapper.insert(user_);
        return Result.ok("注册成功!",null);
    }

    private void saveOnlineUser(User user) {
        OnlineUser onlineUser = new OnlineUser();
        onlineUser.setImg(user.getImg());
        onlineUser.setNickName(user.getNickName());
        onlineUser.setUserId(user.getUserId());
        onlineUsersPools.put(user.getUserId(), onlineUser);
    }

    private User createUser(Long userId, String password) {
        User user = new User();
        user.setPassword(password);
        user.setUserId(userId);
        String nickName = "用户-" + UUID.randomUUID().toString();
        String substring = nickName.substring(0,10);
        user.setNickName(substring);
        user.setImg("https://baomidou.com/assets/asset.cIbiVTt__ZgvyzK.svg");
        return user;
    }
}
