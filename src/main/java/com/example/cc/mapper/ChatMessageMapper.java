package com.example.cc.mapper;

import com.example.cc.pojo.ChatMessage;
import com.example.cc.pojo.ChatMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChatMessageMapper {
    int countByExample(ChatMessageExample example);

    int deleteByExample(ChatMessageExample example);

    int deleteByPrimaryKey(Integer messageId);

    int insert(ChatMessage record);

    int insertSelective(ChatMessage record);

    List<ChatMessage> selectByExample(ChatMessageExample example);

    ChatMessage selectByPrimaryKey(Integer messageId);

    int updateByExampleSelective(@Param("record") ChatMessage record, @Param("example") ChatMessageExample example);

    int updateByExample(@Param("record") ChatMessage record, @Param("example") ChatMessageExample example);

    int updateByPrimaryKeySelective(ChatMessage record);

    int updateByPrimaryKey(ChatMessage record);

    int updateReadFlag(int userId, int toUserId);
}