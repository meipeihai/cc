package com.example.cc.controller;

import com.example.cc.service.UserVerificationService;
import com.example.cc.utils.request.VerificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/verification")
public class UserVerificationController {
    @Autowired
    private UserVerificationService userVerificationService;
    @PostMapping("/verification")
    public ResponseEntity<?> toVerification(@Valid @RequestBody VerificationRequest request) {
        return userVerificationService.toVerification(request);
    }
}
