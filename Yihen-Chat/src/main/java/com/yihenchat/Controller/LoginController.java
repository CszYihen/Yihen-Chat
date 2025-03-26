package com.yihenchat.Controller;


import com.yihenchat.bean.User;
import com.yihenchat.dto.Result;
import com.yihenchat.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        Result result = loginService.login(user.getUserId(), user.getPassword());
        return result;
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        Result result = loginService.register(user.getUserId(), user.getPassword());
        return result;
    }
}
