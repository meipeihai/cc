package com.example.cc.controller;

import com.example.cc.service.CatechismService;
import com.example.cc.service.CommunityService;
import com.example.cc.service.UserService;
import com.example.cc.utils.request.ReleaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/catechism")
public class CatechismController {
    //CatechismService服务层，问答墙相关服务
    @Autowired
    private CatechismService catechismService;
    //发布问题，并写入数据库
    @RequestMapping("/release")
    public ResponseEntity<?> releaseMessage(@Valid @RequestBody ReleaseRequest request){
        return catechismService.releaseMessage(request);
    }
    //从数据库获取内容，并且将一条动态内容作为一个对象，返回前端一个对象数组，在显示时可以取出
    @GetMapping("/getInfo")
    public ResponseEntity<?> getMessage(){
        return catechismService.getMessage();
    }
    //通过用户的id获取用户发布的全部动态
    @PostMapping("/getInfoByUserId")
    public ResponseEntity<?> getInfoByUserId(@Valid @RequestBody ReleaseRequest request){
        return catechismService.getInfoByUserId(request);
    }
    //获取用户的回答
    @GetMapping("/getAnswer")
    public ResponseEntity<?> getAnswerMessage() {
        return catechismService.getAnswerMessage();
    }
    //通过用户的id获取用户发布的全部回答
    @PostMapping("/getAnswerByUserId")
    public ResponseEntity<?> getAnswerByUserId(@Valid @RequestBody ReleaseRequest request) {
        return catechismService.getAnswerByUserId(request);
    }
    //在用户发布的动态下进行评论并写入数据库
    @RequestMapping("/answer")
    public ResponseEntity<?> answerMessage(@Valid @RequestBody ReleaseRequest request){
        return catechismService.answerMessage(request);
    }
    //删除用户发布的问题
    @DeleteMapping("/deleteInfo")
    public ResponseEntity<?> deleteMessage(@Valid @RequestBody ReleaseRequest request) {
        return catechismService.deleteMessage(request);
    }
}
