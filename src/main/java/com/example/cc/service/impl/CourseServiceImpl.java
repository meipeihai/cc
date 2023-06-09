package com.example.cc.service.impl;

import com.example.cc.mapper.CourseMapper;
import com.example.cc.mapper.CourseUserMapper;
import com.example.cc.pojo.Course;
import com.example.cc.pojo.CourseExample;
import com.example.cc.pojo.CourseUserExample;
import com.example.cc.pojo.CourseUserKey;
import com.example.cc.service.CourseService;
import com.example.cc.utils.request.CourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseUserMapper courseUserMapper;
    @Override
    public ResponseEntity<?> createCourse(CourseRequest courseRequest) {
        CourseExample courseExample = new CourseExample();
        Course course = null;
        String code = "";
        int id = -1;
        do {
            Random random = new Random();
            id = random.nextInt(1000) + 1;
            course= courseMapper.selectByPrimaryKey(id);
        }while (course != null);
        course = new Course();
        List<Course> courses = null;
        do {
            code = String.format("%04d", new Random().nextInt(10000));
            courseExample.createCriteria().andCodeEqualTo(code);
            courses = courseMapper.selectByExample(courseExample);
        }while (!courses.isEmpty());
        course.setCourseId(id);
        course.setName(courseRequest.getName());
        course.setCode(code);
        course.setMajorId(courseRequest.getMajorId());
        course.setNumber(course.getNumber());
        courseMapper.insert(course);
        return ResponseEntity.ok("成功创建课程");
    }

    @Override
    public ResponseEntity<?> joinCourse(CourseRequest courseRequest) {
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andCodeEqualTo(courseRequest.getCode());
        List<Course> courses = courseMapper.selectByExample(courseExample);
        if(courses.isEmpty()){
            return ResponseEntity.badRequest().body("该课程群不存在");
        }
        Course course = courses.get(0);
        course.setNumber(course.getNumber()+1);
        courseMapper.updateByExample(course,courseExample);
        int courseId = course.getCourseId();
        CourseUserKey courseUserKey = new CourseUserKey();
        courseUserKey.setCourseId(courseId);
        courseUserKey.setUserId(courseRequest.getUserId());
        courseUserMapper.insert(courseUserKey);
        return ResponseEntity.ok("修改成功");
    }

    public int getCourseId(CourseRequest courseRequest){
        //通过用户id查寻课程id
        CourseUserExample courseUserExample = new CourseUserExample();
        courseUserExample.createCriteria().andUserIdEqualTo(courseRequest.getUserId());
        List<CourseUserKey> courseUserKeys = courseUserMapper.selectByExample(courseUserExample);
        //通过课程名查询课程id
        CourseExample courseExample1 = new CourseExample();
        courseExample1.createCriteria().andNameEqualTo(courseRequest.getName());
        List<Course> courses1 = courseMapper.selectByExample(courseExample1);
        //存储课程id
        int courseId = -1;
        //通过比对两个查出来的id相等，则确定课程id
        for(CourseUserKey c: courseUserKeys){
            for(Course c1: courses1){
                if(c.getCourseId()==c1.getCourseId()){
                    courseId = c.getCourseId();
                    return courseId;
                }
            }
        }
        return -1;
    }
    @Override
    public ResponseEntity<?> removeCourse(CourseRequest courseRequest) {
        int courseId = getCourseId(courseRequest);
        courseMapper.deleteByPrimaryKey(courseId);
        CourseUserExample courseUserExample = new CourseUserExample();
        courseUserExample.createCriteria().andCourseIdEqualTo(courseId);
        courseUserMapper.deleteByExample(courseUserExample);
        return ResponseEntity.ok("移除成功");
    }

    @Override
    public ResponseEntity<?> exitCourse(CourseRequest courseRequest) {

        CourseUserExample courseUserExample = new CourseUserExample();
        courseUserExample.createCriteria().andUserIdEqualTo(courseRequest.getUserId());
        int i = courseUserMapper.deleteByExample(courseUserExample);
        if(i==0){
            return ResponseEntity.badRequest().body("删除失败,你未在该群中");
        }
        return ResponseEntity.ok("退出成功");
    }
}
