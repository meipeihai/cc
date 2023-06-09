package com.example.cc.controller;

import com.example.cc.mapper.SessionMapper;
import com.example.cc.mapper.UserMapper;
import com.example.cc.pojo.Session;
import com.example.cc.pojo.SessionExample;
import com.example.cc.pojo.User;
import com.example.cc.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sessionList")
public class SessionController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SessionMapper sessionMapper;

    /**
     * 已建立会话
     */
    @PostMapping("/already")
    public ResponseEntity<?> sessionListAlready(@RequestParam Integer id){
        List<Session> sessionLists = sessionMapper.selectByUserId(id);
        return  ResponseEntity.ok(sessionLists);
    }

    // 可建立会话的用户
    @PostMapping("/not")
    public ResponseEntity<?> sessionListNot(@RequestParam Integer id){
        SessionExample sessionExample = new SessionExample();
        sessionExample.createCriteria().andUserIdEqualTo(id);
        //获取用户列表中可建立会话的用户id
        List<Session> list = sessionMapper.selectByExample(sessionExample);
        List<Integer> ids = new ArrayList<>();
        for(Session s : list){
            ids.add(s.getToUserId());
        }

        ids.add(id);
        UserExample userExample = new UserExample();
        List<User> users = new ArrayList<>();
        for(int Id:ids){
            userExample.createCriteria().andUserIdEqualTo(Id);
            User user = userMapper.selectByExample(userExample).get(0);
            users.add(user);
        }
        return ResponseEntity.ok(users);
    }

    // 创建会话
    @PostMapping("/createSession")
    public ResponseEntity<?> createSession(@RequestParam Integer id,@RequestParam Integer toUserId,@RequestParam String toUserName){
        com.example.cc.pojo.Session sessionList = new Session();
        sessionList.setUserId(id);
        sessionList.setUnReadCount(0);
        sessionList.setListName(toUserName);
        sessionList.setToUserId(toUserId);
        sessionMapper.insert(sessionList);
        // 判断对方和我建立会话没有？ 没有也要建立
        SessionExample sessionExample = new SessionExample();
        sessionExample.createCriteria().andToUserIdEqualTo(toUserId).andUserIdEqualTo(id);
        Integer integer = sessionMapper.selectByExample(sessionExample).get(0).getSessionId();
        if (integer == null || integer <= 0){
            User user = userMapper.selectByPrimaryKey(id);
            sessionList.setUserId(toUserId);
            sessionList.setToUserId(id);
            sessionList.setListName(user.getName());
            sessionMapper.insert(sessionList);
        }
        return ResponseEntity.ok("建立成功");
    }

    // 删除会话
    @PostMapping("/delSession")
    public ResponseEntity<?> delSession(@RequestParam Integer sessionId){
        sessionMapper.deleteByPrimaryKey(sessionId);
        return ResponseEntity.ok("删除成功");
    }
}
