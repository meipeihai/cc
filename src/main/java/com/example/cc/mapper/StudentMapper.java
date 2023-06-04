package com.example.cc.mapper;

import com.example.cc.pojo.Student;
import java.util.List;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer studentId);

    int insert(Student record);

    Student selectByPrimaryKey(Integer studentId);

    List<Student> selectAll();

    int updateByPrimaryKey(Student record);
}