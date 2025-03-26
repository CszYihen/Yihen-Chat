package com.yihenchat.Controller;

import com.yihenchat.dto.Result;
import com.yihenchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("info/{id}")
    public Result getUserInfo(@PathVariable Long id) {
        return userService.getUserInfo(id);
    }


}
