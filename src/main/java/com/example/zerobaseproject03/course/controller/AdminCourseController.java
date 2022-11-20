package com.example.zerobaseproject03.course.controller;

import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.model.CourseInput;
import com.example.zerobaseproject03.course.model.CourseParam;
import com.example.zerobaseproject03.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/admin")

public class AdminCourseController extends BaseController {


    private final CourseService courseService;

    // 등록한 강좌 목록 가져오는 메소드
    @GetMapping("/course/list.do")
    public String list(Model model, CourseParam parameter) {

        parameter.init();
        List<CourseDto> courseList = courseService.list(parameter);

        long totalCount = 0;

        if (!CollectionUtils.isEmpty(courseList)) {
            totalCount = courseList.get(0).getTotalCount();
        }

        long pageSize = parameter.getPageSize();
        long pageIndex = parameter.getPageIndex();
        String queryString = parameter.getQueryString();

        String pageHtml = getPagerHtml(totalCount, pageSize, pageIndex, queryString);

        model.addAttribute("members", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pageHtml);

        return "admin/course/list";


    }

    // 강좌 등록 페이지로 이동 시키는 메소드
    // 추가 == 수정(비슷한 맥락)
    // GetMapping이 두 주소를 받을 때는 value={...}
    // 신규 추가 => 그냥 추가
    // 수정 => 기존 데이터에 덧붙이기


    // 강좌 등록, 수정하는 메소드(로직 처리 전)
    @GetMapping(value = {"/course/add.do", "/course/edit.do"})
    public String add(Model model, HttpServletRequest request
            , CourseInput parameter) {

        // client 부터 요청한 주소를 이용해 [추가 모드] or [수정 모드] 판단
        boolean editMode = request.getRequestURI().contains("/edit.do");
        CourseDto detail = new CourseDto();

        // 해당 요청 == [수정 모드]
        if (editMode) {

            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);

            // 수정 하려고 하는 강좌(id) 가 존재하지 않는 경우
            if (existCourse == null) {
                // error 처리
                // template/common/error.html
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }

            // 수정 하려고 하는 강좌(id) 가 존재 하는 경우 => 정상적인 수정
            detail = existCourse;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "admin/course/add";

    }

    // 강좌 등록, 수정하는 메소드(로직 처리 후)
    @PostMapping(value = {"/course/add.do", "/course/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request,
                            CourseInput parameter) {


        // client 부터 요청한 주소를 이용해 [추가 모드] or [수정 모드] 판단
        boolean editMode = request.getRequestURI().contains("/edit.do");
        CourseDto detail = new CourseDto();

        // 해당 요청 == [수정 모드]
        if (editMode) {

            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);

            // 수정 하려고 하는 강좌(id) 가 존재하지 않는 경우
            if (existCourse == null) {
                // error 처리
                // template/common/error.html
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = courseService.set(parameter);

        // 해당 요청 == [삽입 모드]
        } else{
            boolean result = courseService.add(parameter);
        }

        return "redirect:/admin/course/list.do";

    }

}
