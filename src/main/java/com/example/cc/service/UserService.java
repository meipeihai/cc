package com.example.cc.service;

import com.example.cc.pojo.User;
import com.example.cc.utils.request.LoginRequest;
import com.example.cc.utils.request.ResetRequest;
import com.example.cc.utils.request.SignupRequest;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
//用户服务接口
public interface UserService {
    ResponseEntity<?> registerUser(SignupRequest request);
    ResponseEntity<?> authenticateUser(@Valid LoginRequest request);
    ResponseEntity<?> resetPassword(@Valid ResetRequest request);
    // 根据手机号码查找用户
    User findByPhoneNumber(String phoneNumber);
    // 生成并发送验证码给用户
    void sendVerificationCode(String phoneNumber);

}
