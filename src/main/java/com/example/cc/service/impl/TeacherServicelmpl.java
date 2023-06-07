package com.example.cc.service.impl;

import com.example.cc.mapper.TeacherMapper;
import com.example.cc.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServicelmpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
}

