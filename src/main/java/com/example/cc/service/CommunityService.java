package com.example.cc.service;

import com.example.cc.utils.request.ReleaseRequest;
import org.springframework.http.ResponseEntity;
//社区服务接口
public interface CommunityService {
    //发布动态
    ResponseEntity<?> releaseMessage(ReleaseRequest request);
    //获取动态数组
    ResponseEntity<?> getMessage();
    //通过ID获取全部动态
    ResponseEntity<?> getMessagesById(ReleaseRequest request);
    //获取社区的用户回答
    ResponseEntity<?> getAnswer();
    //通过用户ID获取用户的全部回答
    ResponseEntity<?> getAnswerById(ReleaseRequest request);
    //提问相关：
    //删除用户的问题
    ResponseEntity<?> deleteMessage(ReleaseRequest request);
    //在用户的提问下回答问题
    ResponseEntity<?> answerMessage(ReleaseRequest request);
}
