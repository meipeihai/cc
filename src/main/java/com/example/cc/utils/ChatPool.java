package com.example.cc.utils;

import com.example.cc.controller.ChatController;
import com.example.cc.pojo.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatPool {
    public static Map<Integer, ChatController> webSockets = new ConcurrentHashMap<>();
    // list 里面第一个存sessionId，第二个存session
    public static Map<Integer, List<Object>> sessionPool = new ConcurrentHashMap<>();
    // 当前登录用户x
    public static Map<String, User> chatUserPool = new ConcurrentHashMap<>();
}
