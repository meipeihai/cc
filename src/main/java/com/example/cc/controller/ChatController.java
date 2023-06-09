package com.example.cc.controller;

import com.example.cc.mapper.ChatMessageMapper;
import com.example.cc.mapper.SessionMapper;
import com.example.cc.mapper.UserMapper;
import com.example.cc.pojo.ChatMessage;
import com.example.cc.pojo.SessionExample;
import com.example.cc.pojo.User;
import com.example.cc.pojo.UserExample;
import com.example.cc.utils.ChatPool;
import com.example.cc.utils.JsonUtils;
import com.example.cc.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
@ServerEndpoint("/api/chat/{userId}/{sessionId}")
public class ChatController {
    //会话mapper,包括当前用户和接收者的userid,
    @Autowired
    private SessionMapper sessionMapper;
    //信息mapper,信息的相关内容
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    //用户mapper
    @Autowired
    private UserMapper userMapper;

    private Session session;
    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")Integer userId, @PathParam(value="sessionId")String sessionId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userId);
        if(userMapper == null){
            userMapper = (UserMapper) SpringContextUtil.getBean("userMapper");
        }
        User user = userMapper.selectByExample(userExample).get(0);
        this.session = session;
        ChatPool.webSockets.put(userId,this);
        List<Object> list = new ArrayList<>();
        list.add(sessionId);
        list.add(session);
        ChatPool.sessionPool.put(userId,list);
        ChatPool.chatUserPool.put(user.getUsername(),user);
        System.out.println("有新的连接，新的连接总数为："+ChatPool.webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        //获取sessionId
        String sessionId = this.session.getRequestParameterMap().get("sessionId").get(0);

        if(sessionId==null){
            System.out.println("sessionId 错误");
        }
        // 在这里无法注入Mapper所以使用这种方式注入Mapper
        if (sessionMapper == null){
            this.sessionMapper = (SessionMapper)SpringContextUtil.getBean("sessionMapper");
        }
        if (userMapper == null){
            this.userMapper = (UserMapper)SpringContextUtil.getBean("userMapper");
        }
        if (chatMessageMapper == null){
            this.chatMessageMapper = (ChatMessageMapper)SpringContextUtil.getBean("chatMessageMapper");
        }
        com.example.cc.pojo.Session sessionList = sessionMapper.selectByPrimaryKey(Integer.parseInt(sessionId));
        User user = userMapper.selectByPrimaryKey(sessionList.getUserId());
        ChatMessage msgInfo = new ChatMessage();
        msgInfo.setContent(message);
        msgInfo.setCreateTime(new Date());
        msgInfo.setFromUserId(sessionList.getUserId());
        msgInfo.setFromUserName(user.getName());
        msgInfo.setToUserId(sessionList.getToUserId());
        //listName：会话的名称
        msgInfo.setToUserName(sessionList.getListName());
        msgInfo.setUnReadFlag(0);
        //信息持久化
        chatMessageMapper.insert(msgInfo);

        // 判断接收者用户是否存在，不存在就结束
        List<Object> list = ChatPool.sessionPool.get(sessionList.getToUserId());
        if (list == null || list.isEmpty()){
            // 用户不存在，更新未读数
            sessionMapper.addUnReadCount(sessionList.getToUserId(),sessionList.getUserId());
        }else{
            // 用户存在，判断会话是否存在
            SessionExample sessionExample = new SessionExample();
            sessionExample.createCriteria().andUserIdEqualTo(sessionList.getUserId()).andToUserIdEqualTo(sessionList.getToUserId());
            //获取两者对应会话的id
            String id = sessionMapper.selectByExample(sessionExample).get(0).getSessionId()+"";
            //list中第一位是sessionId,第二位中存储着对应的session
            String o = list.get(0) + "";
            if (id.equals(o)){
                // 会话存在直接发送消息
                sendTextMessage(sessionList.getToUserId(), JsonUtils.objectToJson(msgInfo));
            }else {
                // 判断会话列表是否存在
                if (id == null || "".equals(id) || "null".equals(id)){
                    // 新增会话列表
                    com.example.cc.pojo.Session tmpSessionList = new com.example.cc.pojo.Session();
                    tmpSessionList.setUserId(sessionList.getToUserId());
                    tmpSessionList.setToUserId(sessionList.getUserId());
                    tmpSessionList.setListName(user.getName());
                    tmpSessionList.setUnReadCount(1);
                    sessionMapper.insert(tmpSessionList);
                }else {
                    // 更新未读消息数量
                    sessionMapper.addUnReadCount(sessionList.getToUserId(),sessionList.getUserId());
                }
                // 会话不存在发送列表消息
                List<com.example.cc.pojo.Session> sessionLists = sessionMapper.selectByUserId(sessionList.getToUserId());
                sendTextMessage(sessionList.getToUserId() ,JsonUtils.objectToJson(message));
            }
        }
        System.out.println("【websocket消息】收到客户端消息:"+message);
    }

    @OnClose
    public void onClose() {
        // 断开连接删除用户删除session
        Integer userId = Integer.parseInt(this.session.getRequestParameterMap().get("userId").get(0));
        ChatPool.sessionPool.remove(userId);
        ChatPool.webSockets.remove(userId);
        if (userMapper == null){
            this.userMapper = (UserMapper) SpringContextUtil.getBean("userMapper");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        ChatPool.chatUserPool.remove(user.getName());
        System.out.println("【websocket消息】连接断开，总数为:"+ChatPool.webSockets.size());
    }

    public void sendTextMessage(int userId, String message) {
        Session session = (Session)ChatPool.sessionPool.get(userId).get(1);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
