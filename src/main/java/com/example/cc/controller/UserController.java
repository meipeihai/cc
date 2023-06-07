package com.example.cc.controller;

import com.example.cc.service.UserService;
import com.example.cc.utils.request.LoginRequest;
import com.example.cc.utils.request.ResetRequest;
import com.example.cc.utils.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")

public class UserController {
    //UserService服务层，对用户的相关登陆注册服务实现
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request){
        return userService.registerUser(request);
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request){
        return userService.authenticateUser(request);
    }
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetRequest request) {
        return userService.resetPassword(request);
    }
    //从后端发送验证码到用户的邮箱或电话号码
    @GetMapping("/sendCode")
    public void sendCode(@Valid @RequestBody  ResetRequest request){
         userService.sendVerificationCode(request.getPhoneNumber());
    }

}
