package com.example.cc.utils.request;

import lombok.Data;

@Data
public class VerificationRequest {
    private String email;
    private String Department;
    private String major;
    private int userId;

}
