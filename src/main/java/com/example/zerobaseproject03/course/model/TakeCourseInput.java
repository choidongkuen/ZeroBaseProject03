package com.example.zerobaseproject03.course.model;


import com.example.zerobaseproject03.admin.model.CommonParam;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data

// 수강신청을 위한 클래스
public class TakeCourseInput{

    long courseId;
    String userId;
}
