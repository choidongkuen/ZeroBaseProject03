package com.example.zerobaseproject03.course.controller;

import com.example.zerobaseproject03.admin.service.CategoryService;
import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.dto.TakeCourseDto;
import com.example.zerobaseproject03.course.model.ServiceResult;
import com.example.zerobaseproject03.course.model.TakeCourseParam;
import com.example.zerobaseproject03.course.service.CourseService;
import com.example.zerobaseproject03.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/admin")

public class AdminTakeCourseController extends BaseController {


    private final TakeCourseService takeCourseService; // 수강 강좌 서비스
    private final CategoryService categoryService; // 카테고리 서비스
    private final CourseService courseService;

    // 회원이 신청한 수강 항목 관리하는 메소드(관리자용)
    @GetMapping("/takecourse/list.do")
    public String list(Model model,
                       TakeCourseParam parameter,
                       BindingResult bindingResult) {



        parameter.init();
        List<TakeCourseDto> takeCourseList = takeCourseService.list(parameter);

        long totalCount = 0;

        if (!CollectionUtils.isEmpty(takeCourseList)) {
            totalCount = takeCourseList.get(0).getTotalCount();
        }

        long pageSize = parameter.getPageSize();
        long pageIndex = parameter.getPageIndex();
        String queryString = parameter.getQueryString();

        String pageHtml = getPagerHtml(totalCount, pageSize, pageIndex, queryString);

        model.addAttribute("members", takeCourseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pageHtml);

        List<CourseDto> courseList = courseService.listAll();
        model.addAttribute("courseList",courseList);

        return "admin/takecourse/list";

    }
    // 관리자 메뉴에서 수강 관리에서 결제 전인 강좌를 결제 완료 or 수강 취소 할 때 처리하는 컨트롤러
    @PostMapping("/takecourse/status.do")
    public String status(Model model, TakeCourseParam parameter) {

        parameter.init();
        ServiceResult serviceResult =
                takeCourseService.updateStatus(parameter.getId(),parameter.getStatus());


        if(!serviceResult.isResult()){
            model.addAttribute("message", serviceResult.getMessage());
            return "common/error";
        }

        return "redirect:/admin/takecourse/list.do";

    }
}


