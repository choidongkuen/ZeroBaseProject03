package com.example.zerobaseproject03.admin.mapper;


import com.example.zerobaseproject03.admin.dto.MemberDto;

import com.example.zerobaseproject03.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// Mapper : 데이터베이스에 대한 쿼리 실행 가능(using Mybatis)
@Mapper
public interface MemberMapper {


    // MemberMapper.xml에서 각 select 태그안에 id는
    // 메소드 이름이다.(쿼리를 실행하는 메소드)

    long selectListCount(MemberParam paramter);
    List<MemberDto> selectList(MemberParam parameter);

}
