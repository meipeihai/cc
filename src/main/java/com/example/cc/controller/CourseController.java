package com.example.cc.controller;

import com.example.cc.service.CourseService;
import com.example.cc.utils.request.CourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @RequestMapping("/create")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        return courseService.createCourse(courseRequest);
    }
    @RequestMapping("/join")
    public ResponseEntity<?> joinCourse(@Valid @RequestBody CourseRequest courseRequest) {
        return courseService.joinCourse(courseRequest);
    }
    @RequestMapping("/remove")
    public ResponseEntity<?> removeCourse(@Valid @RequestBody CourseRequest courseRequest){
         return courseService.removeCourse(courseRequest);
    }
    @RequestMapping("/exit")
    public ResponseEntity<?> exitCourse(@Valid @RequestBody CourseRequest courseRequest) {
        return courseService.exitCourse(courseRequest);
    }
}
