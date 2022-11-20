package com.example.zerobaseproject03.course.service;

import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.model.CourseInput;
import com.example.zerobaseproject03.course.model.CourseParam;

import java.util.List;

public interface CourseService {

    // 입력된 강좌명 추가
    boolean add(CourseInput parameter);

    // CourseRepository에서 저장된 Course 정보들을 가져옴
    List<CourseDto> list(CourseParam parameter);


    // 아이디를 통해서 강좌 상세 정보를 가져옴

    CourseDto getById(long id);
}
