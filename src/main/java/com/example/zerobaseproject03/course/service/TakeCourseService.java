package com.example.zerobaseproject03.course.service;

import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.dto.TakeCourseDto;
import com.example.zerobaseproject03.course.model.*;

import java.util.List;

public interface TakeCourseService {



    // 회원들의 수강목록
    List<TakeCourseDto> list(TakeCourseParam parameter);


}
