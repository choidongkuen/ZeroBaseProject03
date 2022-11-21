package com.example.zerobaseproject03.course.mapper;


import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.dto.TakeCourseDto;
import com.example.zerobaseproject03.course.model.CourseParam;
import com.example.zerobaseproject03.course.model.TakeCourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TakeCourseMapper {

    long selectListCount(TakeCourseParam parameter);
    List<TakeCourseDto> selectList(TakeCourseParam parameter);
}
