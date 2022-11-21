package com.example.zerobaseproject03.course.service;

import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.dto.TakeCourseDto;
import com.example.zerobaseproject03.course.model.*;

import java.util.List;

public interface TakeCourseService {



    // 회원들의 수강목록
    List<TakeCourseDto> list(TakeCourseParam parameter);
    

    // 해당 id 회원의 수강 상태 변경
    ServiceResult updateStatus(long id, String status);

    // 내 수강목록
    List<TakeCourseDto> myCourse(String userId);

    // 수강 상세 정보
    TakeCourseDto detail(long id);

    // 수강 삭제
    ServiceResult cancel(long courseId);


}
