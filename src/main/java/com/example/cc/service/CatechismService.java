package com.example.cc.service;

import com.example.cc.utils.request.ReleaseRequest;
import org.springframework.http.ResponseEntity;
//问答墙服务接口
public interface CatechismService {
    //发布问题
    ResponseEntity<?> releaseMessage(ReleaseRequest request);

    ResponseEntity<?> getMessage();

    ResponseEntity<?> getAnswerMessage();

    ResponseEntity<?> answerMessage(ReleaseRequest request);

    ResponseEntity<?> deleteMessage(ReleaseRequest request);

    ResponseEntity<?> getInfoByUserId(ReleaseRequest request);

    ResponseEntity<?> getAnswerByUserId(ReleaseRequest request);
}
