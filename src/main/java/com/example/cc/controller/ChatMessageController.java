package com.example.cc.controller;

import com.example.cc.mapper.ChatMessageMapper;
import com.example.cc.mapper.SessionMapper;
import com.example.cc.pojo.ChatMessage;
import com.example.cc.pojo.ChatMessageExample;
import com.example.cc.pojo.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatMessageController {
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private SessionMapper sessionMapper;
    // 消息列表
    @PostMapping("/chatList")
    public ResponseEntity<?> msgList(@RequestParam Integer sessionId){
        Session sessionList = sessionMapper.selectByPrimaryKey(sessionId);
        if(sessionList == null){
            return ResponseEntity.ok("不存在信息列表");
        }
        Integer fromUserId = sessionList.getUserId();
        Integer toUserId = sessionList.getToUserId();
        ChatMessageExample chatMessageExample = new ChatMessageExample();
        chatMessageExample.createCriteria().andFromUserIdEqualTo(fromUserId).andToUserIdEqualTo(toUserId);
        List<ChatMessage> msgInfoList = chatMessageMapper.selectByExample(chatMessageExample);
        // 更新消息已读
        chatMessageMapper.updateReadFlag(fromUserId, toUserId);
        // 更新会话里面的未读消息
        sessionMapper.delUnReadCount(fromUserId, toUserId);
        return ResponseEntity.ok(msgInfoList);
    }
}
