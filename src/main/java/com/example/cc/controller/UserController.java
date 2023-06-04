package com.example.cc.controller;

import com.example.cc.mapper.UserMapper;
import com.example.cc.pojo.User;
import com.example.cc.pojo.UserExample;
import com.example.cc.service.UserService;
import com.example.cc.utils.LoginRequest;
import com.example.cc.utils.MessageResponse;
import com.example.cc.utils.SignupRequest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController {

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
}
