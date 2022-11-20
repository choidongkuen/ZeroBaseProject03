package com.example.zerobaseproject03.admin.mapper;


import com.example.zerobaseproject03.admin.dto.CategoryDto;
import com.example.zerobaseproject03.admin.dto.MemberDto;
import com.example.zerobaseproject03.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// Mapper : 데이터베이스에 대한 쿼리 실행 가능(using Mybatis)
@Mapper
public interface CategoryMapper {

    List<CategoryDto> select(CategoryDto parameter);
}
