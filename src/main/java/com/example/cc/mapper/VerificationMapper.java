package com.example.cc.mapper;

import com.example.cc.pojo.Verification;
import com.example.cc.pojo.VerificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VerificationMapper {
    int countByExample(VerificationExample example);

    int deleteByExample(VerificationExample example);

    int deleteByPrimaryKey(Integer verificationId);

    int insert(Verification record);

    int insertSelective(Verification record);

    List<Verification> selectByExample(VerificationExample example);

    Verification selectByPrimaryKey(Integer verificationId);

    int updateByExampleSelective(@Param("record") Verification record, @Param("example") VerificationExample example);

    int updateByExample(@Param("record") Verification record, @Param("example") VerificationExample example);

    int updateByPrimaryKeySelective(Verification record);

    int updateByPrimaryKey(Verification record);
}