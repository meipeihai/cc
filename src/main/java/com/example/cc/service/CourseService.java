package com.example.cc.service;

import com.example.cc.utils.request.CourseRequest;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    ResponseEntity<?> createCourse(CourseRequest courseRequest);

    ResponseEntity<?> joinCourse(CourseRequest courseRequest);

    ResponseEntity<?> removeCourse(CourseRequest courseRequest);

    ResponseEntity<?> exitCourse(CourseRequest courseRequest);
}
