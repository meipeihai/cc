package com.example.cc.utils.request;

import lombok.Data;

//课程存储信息
@Data
public class CourseRequest {
    //课程id
    private int number = 0;
    private String name;
    private int majorId;
    private String code;
    private int userId;
}
