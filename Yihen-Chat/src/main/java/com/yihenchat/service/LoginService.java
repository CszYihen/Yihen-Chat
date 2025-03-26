package com.yihenchat.service;

import com.yihenchat.bean.User;
import com.yihenchat.dto.Result;

public interface LoginService {
    public Result login(Long userId, String password);

    public Result register(Long userId, String password);
}
