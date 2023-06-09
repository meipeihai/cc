package com.example.cc.mapper;

import com.example.cc.pojo.CourseUserExample;
import com.example.cc.pojo.CourseUserKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseUserMapper {
    int countByExample(CourseUserExample example);

    int deleteByExample(CourseUserExample example);

    int deleteByPrimaryKey(CourseUserKey key);

    int insert(CourseUserKey record);

    int insertSelective(CourseUserKey record);

    List<CourseUserKey> selectByExample(CourseUserExample example);

    int updateByExampleSelective(@Param("record") CourseUserKey record, @Param("example") CourseUserExample example);

    int updateByExample(@Param("record") CourseUserKey record, @Param("example") CourseUserExample example);
}