package com.example.cc.service.impl;

import com.example.cc.mapper.DepartmentMapper;
import com.example.cc.mapper.MajorMapper;
import com.example.cc.pojo.Department;
import com.example.cc.pojo.Major;
import com.example.cc.service.UserVerificationService;
import com.example.cc.utils.request.VerificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserVerificationServiceImpl implements UserVerificationService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Override
    public ResponseEntity<?> toVerification(VerificationRequest request) {
        //生成department和major的id
        Department department = null;
        Major major =null;
        int majorId = -1;
        do {
            Random random = new Random();
            majorId = random.nextInt(1000) + 1;
            major= majorMapper.selectByPrimaryKey(majorId);
        }while (major != null);
        int departmentId = -1;
        do {
            Random random = new Random();
            departmentId = random.nextInt(1000) + 1;
            department= departmentMapper.selectByPrimaryKey(departmentId);
        }while (department != null);
        //new两个对象
        major = new Major();
        department = new Department();
        //设置参数并存入数据库
        department.setDepartmentId(departmentId);
        department.setName(request.getDepartment());
        departmentMapper.insert(department);
        major.setMajorId(majorId);
        major.setName(request.getMajor());
        major.setDepartmentId(departmentId);
        majorMapper.insert(major);
        return ResponseEntity.ok("验证成功");
    }

}
