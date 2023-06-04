package com.example.cc.service;

import com.example.cc.utils.LoginRequest;
import com.example.cc.utils.SignupRequest;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public interface UserService {
    ResponseEntity<?> registerUser(SignupRequest request);
    ResponseEntity<?> authenticateUser(@Valid LoginRequest request);
}
