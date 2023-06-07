package com.example.cc.service.impl;

import com.example.cc.mapper.AnswerMapper;
import com.example.cc.mapper.MessageMapper;
import com.example.cc.pojo.*;
import com.example.cc.service.CatechismService;
import com.example.cc.utils.request.ReleaseRequest;
import com.example.cc.utils.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CatechismServiceImpl implements CatechismService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Override
    public ResponseEntity<?> releaseMessage(ReleaseRequest request) {
        if(request.getMessage()==null){
            return ResponseEntity.badRequest().body(new MessageResponse("内容为空"));
        }

        Message message = null;
        int id = -1;
        do {
            Random random = new Random();
            id = random.nextInt(1000) + 1;
            message= messageMapper.selectByPrimaryKey(id);
        }while (message != null);
        message = new Message();
        message.setInfo(request.getMessage());
        message.setMessageId(id);
        message.setUserId(request.getUserId());
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        time.format(date);
        message.setTime(date);
        messageMapper.insert(message);
        return ResponseEntity.ok(new MessageResponse("发布成功"));
    }

    @Override
    public ResponseEntity<?> getMessage() {
        MessageExample messageExample = new MessageExample();
        MessageExample.Criteria criteria = messageExample.createCriteria();
        messageExample.setOrderByClause("time DESC");
        List<Message> messages = messageMapper.selectByExample(messageExample);
        //返回问答信息中的列表以便于显示
        return ResponseEntity.ok(messages);
    }

    @Override
    public ResponseEntity<?> getAnswerMessage() {
        AnswerExample answerExample= new AnswerExample();
        AnswerExample.Criteria criteria = answerExample.createCriteria();
        answerExample.setOrderByClause("time DESC");
        List<Answer> messages = answerMapper.selectByExample(answerExample);
        //返回问答信息中的列表以便于显示
        return ResponseEntity.ok(messages);
    }
    @Override
    public ResponseEntity<?> answerMessage(ReleaseRequest request) {
        if(request.getMessage()==null){
            return ResponseEntity.badRequest().body(new MessageResponse("内容为空"));
        }
        Answer answer = null;
        int id = -1;
        do {
            Random random = new Random();
            id = random.nextInt(1000) + 1;
            answer= answerMapper.selectByPrimaryKey(id);
        }while (answer != null);
        answer = new Answer();
        answer.setInfo(request.getMessage());
        answer.setMessageId(request.getMessageId());
        answer.setUserId(request.getUserId());
        answer.setAnswerId(id);
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        time.format(date);
        answer.setTime(date);
        answerMapper.insert(answer);
        return ResponseEntity.ok(new MessageResponse("发布成功"));
    }

    @Override
    public ResponseEntity<?> deleteMessage(ReleaseRequest request) {
        Message message = messageMapper.selectByPrimaryKey(request.getMessageId());
        if(message == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("没有该对象"));
        }
        int i = messageMapper.deleteByPrimaryKey(request.getMessageId());
        return ResponseEntity.ok(new MessageResponse(i+""));
    }

    @Override
    public ResponseEntity<?> getInfoByUserId(ReleaseRequest request) {
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andUserIdEqualTo(request.getUserId());
        List<Message> messages = messageMapper.selectByExample(messageExample);
        return ResponseEntity.ok(messages);
    }

    @Override
    public ResponseEntity<?> getAnswerByUserId(ReleaseRequest request) {
        AnswerExample answerExample = new AnswerExample();
        answerExample.createCriteria().andUserIdEqualTo(request.getUserId());
        List<Answer> answers = answerMapper.selectByExample(answerExample);
        return ResponseEntity.ok(answers);
    }
}
