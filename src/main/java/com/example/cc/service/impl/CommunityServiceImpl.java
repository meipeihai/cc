package com.example.cc.service.impl;

import com.example.cc.mapper.MessageMapper;
import com.example.cc.pojo.Message;
import com.example.cc.pojo.MessageExample;
import com.example.cc.service.CommunityService;
import com.example.cc.utils.request.ReleaseRequest;
import com.example.cc.utils.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private MessageMapper messageMapper;
    //发布动态
    @Override
    public ResponseEntity<?> releaseMessage(ReleaseRequest request) {
        if(request == null){
            return ResponseEntity.badRequest().body(new MessageResponse("内容为空"));
        }
        Message message = null;
        int ID = -1;
        do{
            Random random = new Random();
            ID = random.nextInt(1000)+1;
        }while((messageMapper.selectByPrimaryKey(ID))!=null);
        message = new Message();
        message.setUserId(request.getUserId());
        message.setMessageId(ID);
        message.setInfo(request.getMessage());
        message.setTime(new Date());
        /*
        if(request.getCourseId()!=null){
            message.setCourseId(request.getCourseId());
        }
        判断社区id
        if(request.getCommunityId()!=null){
            message.setCommunityId(request.getCommunityId());
        }
         */
        messageMapper.insert(message);
        return ResponseEntity.ok(new MessageResponse("发布成功"));
    }
    //获取动态数组
    @Override
    public ResponseEntity<?> getMessage() {
        MessageExample messageExample = new MessageExample();
        messageExample.setOrderByClause("time DESC");
        List<Message> messages = messageMapper.selectByExample(messageExample);
        return ResponseEntity.ok(messages);
    }
    //通过Id获取全部动态
    @Override
    public ResponseEntity<?> getMessagesById(ReleaseRequest request) {
        return null;
    }
    //获取社区的用户回答
    @Override
    public ResponseEntity<?> getAnswer() {
        return null;
    }
    //通过用户Id获取用户的全部回答
    @Override
    public ResponseEntity<?> getAnswerById(ReleaseRequest request) {
        return null;
    }
    //删除用户问题
    @Override
    public ResponseEntity<?> deleteMessage(ReleaseRequest request) {
        return null;
    }
    //在用户的问题下回答问题
    @Override
    public ResponseEntity<?> answerMessage(ReleaseRequest request) {
        return null;
    }
}
