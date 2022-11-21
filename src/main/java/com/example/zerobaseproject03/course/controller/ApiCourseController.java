package com.example.zerobaseproject03.course.controller;

import com.example.zerobaseproject03.admin.service.CategoryService;
import com.example.zerobaseproject03.course.model.ServiceResult;
import com.example.zerobaseproject03.course.model.TakeCourseInput;
import com.example.zerobaseproject03.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


// 중요
// Controller -> 뷰 이름(문자열) 리턴
// RestController -> Json 형태 데이터 리턴

@RequiredArgsConstructor
@RestController

public class ApiCourseController extends BaseController {


    private final CourseService courseService; // 강좌 서비스
    private final CategoryService categoryService; // 카테고리 서비스


    // 프론트 페이지에서 회원 강좌 신청하는 메소드(using API)
    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReqeust(Model model,
                                @RequestBody TakeCourseInput parameter,
                                Principal principal) {

        parameter.setUserId(principal.getName());
        ServiceResult serviceResult = courseService.req(parameter);

        if(!serviceResult.isResult()){
            return ResponseEntity.ok().body(serviceResult.getMessage());
        }

        return ResponseEntity.ok().body(parameter);

    }
}
