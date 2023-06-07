package com.example.cc.service;

import com.example.cc.utils.request.VerificationRequest;
import org.springframework.http.ResponseEntity;

public interface UserVerificationService {
    ResponseEntity<?> toVerification(VerificationRequest request);
}
