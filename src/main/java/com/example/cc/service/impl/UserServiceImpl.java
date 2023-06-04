package com.example.cc.service.impl;

import com.example.cc.mapper.UserMapper;
import com.example.cc.pojo.User;
import com.example.cc.pojo.UserExample;
import com.example.cc.service.UserService;
import com.example.cc.utils.*;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public ResponseEntity<?> registerUser(SignupRequest request) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        //邮箱存在与否
        UserExample.Criteria criteria1 = criteria.andEmailEqualTo(request.getEmail());
        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("该邮箱已被注册"));
        }
        //用户名存在与否
        UserExample.Criteria criteria2 = criteria.andUsernameEqualTo(request.getUsername());
        users = userMapper.selectByExample(userExample);
        if(!users.isEmpty()){
            return ResponseEntity.badRequest().body(new MessageResponse("用户名已被注册，请更换"));
        }

        //生成随机id
        User reconUser1 = null;
        int id = -1;
        do {
            Random random = new Random();
            id = random.nextInt(1000) + 1;
            reconUser1= userMapper.selectByPrimaryKey(id);
        }while (reconUser1 != null);
        //设置将要注册的用户数据
        User user = new User();
        user.setUserId(id);
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userMapper.insert(user);

        return ResponseEntity.ok(new MessageResponse("注册成功"));
    }


    public ResponseEntity<?> authenticateUser(LoginRequest request) {
        //通过email查询记录
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        UserExample.Criteria criteria1 = criteria.andEmailEqualTo(request.getEmail());
        List<User> users = userMapper.selectByExample(userExample);
        //由于是email是唯一的，只需要判断list中是否存在数据，判断是否是有效的邮箱
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("无效的邮箱或密码"));
        }

        //获得users中的数据记录
        User user = users.get(0);
        //通过BCryptPasswordEncoder进行解密
//        boolean matches = new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())；
        //从前端进行加密
      boolean matches = user.getPassword().equals(request.getPassword());
        if (!matches) {
            return ResponseEntity.badRequest().body(new MessageResponse("无效的邮箱或密码q"));
        }

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + JwtUtils.EXPIRATION_TIME))
                .signWith(JwtUtils.SIGNATURE_ALGORITHM, JwtUtils.SECRET.getBytes())
                .compact();

        return ResponseEntity.ok(new TokenResponse(token));
    }
}
