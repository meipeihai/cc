package com.example.cc;

import com.example.cc.mapper.UserMapper;
import com.example.cc.pojo.User;
import com.example.cc.pojo.UserExample;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("com.example.cc.mapper")
class CcApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        UserExample.Criteria criteria1 = criteria.andEmailEqualTo("tsy@outlook.com");
        List<User> users = userMapper.selectByExample(userExample);
        for (User user : users) {
            System.out.println(user);
        }
    }

}
