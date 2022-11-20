package com.example.zerobaseproject03.course.service;

import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.model.CourseInput;
import com.example.zerobaseproject03.course.model.CourseParam;

import java.util.List;

public interface CourseService {

    // 입력된 강좌명 추가하는 메소드
    boolean add(CourseInput parameter);


    // 강좌 정보를 수정하는 메소드
    boolean set(CourseInput parameter);

    // CourseRepository에서 저장된 Course 정보들을 가져옴(관리자용)
    List<CourseDto> list(CourseParam parameter);


    // 아이디를 통해서 강좌 상세 정보를 가져옴
    CourseDto getById(long id);

    // 선책한 강좌를 삭제
    boolean del(String idList);


    // 회원의 강좌 리스트를 반환하는 메소드(일반 회원용)
    List<CourseDto> frontList(CourseParam parameter);

}
