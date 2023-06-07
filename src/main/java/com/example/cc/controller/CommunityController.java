package com.example.cc.controller;

import com.example.cc.service.CommunityService;
import com.example.cc.service.UserService;
import com.example.cc.utils.request.ReleaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/community")
public class CommunityController {
    //CommunityService服务层，提供具体的关于用户社区的服务
    @Autowired
    private CommunityService communityService;
    //发布动态
    @RequestMapping("/release")
    public ResponseEntity<?> releaseMessage(@Valid @RequestBody ReleaseRequest request){
        return communityService.releaseMessage(request);
    }
    //从数据库获取动态，并且将一条动态内容作为一个对象，返回前端一个对象数组，在显示时可以取出
    @GetMapping("/getInfo")
    public ResponseEntity<?> getMessage(){
        return communityService.getMessage();
    }
}
