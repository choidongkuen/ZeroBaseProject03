package com.example.zerobaseproject03.course.controller;

import com.example.zerobaseproject03.admin.service.CategoryService;
import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.model.CourseParam;
import com.example.zerobaseproject03.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j

public class CourseController extends BaseController {


    private final CourseService courseService; // 강좌 서비스
    private final CategoryService categoryService; // 카테고리 서비스

    // 프론트 페이지에서 회원 강좌 목록 가져오는 메소드
    @GetMapping("/course")
    public String course(Model model, CourseParam parameter) {

        List<CourseDto> list = courseService.frontList(parameter);
        model.addAttribute("list",list);

        return "course/index";
    }
}
