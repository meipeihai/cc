package com.example.cc.service.impl;

import com.example.cc.mapper.CommunityMapper;
import com.example.cc.mapper.UserMapper;
import com.example.cc.mapper.VerificationMapper;
import com.example.cc.pojo.User;
import com.example.cc.pojo.UserExample;
import com.example.cc.pojo.Verification;
import com.example.cc.service.UserService;
import com.example.cc.utils.*;
import com.example.cc.utils.request.LoginRequest;
import com.example.cc.utils.request.ResetRequest;
import com.example.cc.utils.request.SignupRequest;
import com.example.cc.utils.response.MessageResponse;
import com.example.cc.utils.response.TokenResponse;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    //具体调用user，verification(验证消息）相关的数据库处理语句
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VerificationMapper verificationMapper;
    @Autowired
    private CommunityMapper communityMapper;
    @Override
    public ResponseEntity<?> registerUser(SignupRequest request) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteriaEmail = userExample.createCriteria();
        //手机号存在与否
        UserExample.Criteria criteria1 = criteriaEmail.andPhoneNumberEqualTo(request.getPhoneNumber());
        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("该手机号已被注册"));
        }
        //用户名存在与否
        UserExample userExampleUsername = new UserExample();
        UserExample.Criteria criteriaUsername = userExampleUsername.createCriteria();
        UserExample.Criteria criteria2 = criteriaUsername.andUsernameEqualTo(request.getUsername());
        users = userMapper.selectByExample(userExampleUsername);
        if(!users.isEmpty()){
            return ResponseEntity.badRequest().body(new MessageResponse("用户名已被注册，请更换"));
        }
        String password = new BCryptPasswordEncoder().encode(request.getPassword());
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
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUsername(request.getUsername());
        user.setPassword(password);
        userMapper.insert(user);

        return ResponseEntity.ok(new MessageResponse("注册成功"));
    }

    public ResponseEntity<?> authenticateUser(LoginRequest request) {
        //通过email查询记录
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        UserExample.Criteria criteria1 = criteria.andPhoneNumberEqualTo(request.getPhoneNumber());
        List<User> users = userMapper.selectByExample(userExample);
        //由于是email是唯一的，只需要判断list中是否存在数据，判断是否是有效的邮箱
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("无效的邮箱或密码"));
        }

        //获得users中的数据记录
        User user = users.get(0);
        //通过BCryptPasswordEncoder进行解密
        boolean matches = new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword());
        //从前端进行加密
//      boolean matches = user.getPassword().equals(request.getPassword());
        if (!matches) {
            return ResponseEntity.badRequest().body(new MessageResponse("无效的邮箱或密码"));
        }

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + JwtUtils.EXPIRATION_TIME))
                .signWith(JwtUtils.SIGNATURE_ALGORITHM, JwtUtils.SECRET.getBytes())
                .compact();
        return ResponseEntity.ok(new TokenResponse(token,user.getUserId()));
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetRequest request) {
        User byPhoneNumber = findByPhoneNumber(request.getPhoneNumber());
        byPhoneNumber.setPassword(request.getNewPassword());
        userMapper.updateByPrimaryKey(byPhoneNumber);
        return ResponseEntity.ok(new MessageResponse("重置成功"));
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        UserExample.Criteria criteria1 = criteria.andPhoneNumberEqualTo(phoneNumber);
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0);
    }

    @Override
    public void sendVerificationCode(String phoneNumber) {

        // 从数据库中查找用户
        User user = findByPhoneNumber(phoneNumber);
        // 生成一个六位数的随机验证码
        String code = String.format("%06d", new Random().nextInt(1000000));
        // 设置验证码的过期时间为10分钟后
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(10);
        Date date = Timestamp.valueOf(expiryDate);
        // 创建一个验证码实体并保存到数据库
        Verification verification = new Verification();
        verification.setCode(code);
        verification.setUserId(user.getUserId());
        verification.setExpiryDate(date);
        verificationMapper.insert(verification);
        // 调用Twilio服务发送验证码短信给用户
//        Twilio twilioService = null;
//        twilioService.sendSms(phoneNumber, "Your verification code is: " + code);
    }
}
