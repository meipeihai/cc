package com.example.cc.mapper;

import com.example.cc.pojo.Session;
import com.example.cc.pojo.SessionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SessionMapper {
    int countByExample(SessionExample example);

    int deleteByExample(SessionExample example);

    int deleteByPrimaryKey(Integer sessionId);

    int insert(Session record);

    int insertSelective(Session record);

    List<Session> selectByExample(SessionExample example);

    Session selectByPrimaryKey(Integer sessionId);

    int updateByExampleSelective(@Param("record") Session record, @Param("example") SessionExample example);

    int updateByExample(@Param("record") Session record, @Param("example") SessionExample example);

    int updateByPrimaryKeySelective(Session record);

    int updateByPrimaryKey(Session record);
    int addUnReadCount(int userId, int toUserId);
    
    int delUnReadCount(int userId, int toUserId);

    List<Session> selectByUserId(Integer toUserId);
}