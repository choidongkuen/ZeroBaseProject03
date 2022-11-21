package com.example.zerobaseproject03.course.controller;

import com.example.zerobaseproject03.admin.dto.CategoryDto;
import com.example.zerobaseproject03.admin.service.CategoryService;
import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.model.CourseParam;
import com.example.zerobaseproject03.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j

public class CourseController extends BaseController {


    private final CourseService courseService; // 강좌 서비스
    private final CategoryService categoryService; // 카테고리 서비스



    // 프론트 페이지에서 회원 강좌 목록 가져오는 컨트롤러
    @GetMapping("/course")
    public String course(Model model, CourseParam parameter) {


        List<CourseDto> list = courseService.frontList(parameter);
        model.addAttribute("list", list);

        List<CategoryDto> categoryList
                = categoryService.frontList(CategoryDto.builder().build());

        // 강좌에 총 개수
        int courseTotalCount = 0;
        if(categoryList != null){

            for(CategoryDto dto : categoryList){
                courseTotalCount += dto.getCourseCount();
            }
        }

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("courseTotalCount", courseTotalCount);

        return "course/index";
    }



    // {id} => id는 동적인 값
    // 즉 해당 컨트롤러는 강좌 정보 페이지에서 뜨는 강좌를 클릭시
    // 해당 강좌 정보를 보여준다.

    // front 뷰 구현 => 컨트롤러 구현(구현시 초기에 처리후 보낼 뷰를 테스트) 및 로직 구현 => 뷰 구현
    @GetMapping("/course/{id}")
    public String courseDetail(Model model,
                               CourseParam parameter) {

        CourseDto detail = courseService.frontDetail(parameter.getId());
        model.addAttribute("detail",detail);

        return "course/detail";
    }
}
