package com.example.zerobaseproject03.admin.controller;

import com.example.zerobaseproject03.admin.dto.MemberDto;
import com.example.zerobaseproject03.admin.model.MemberParam;
import com.example.zerobaseproject03.admin.model.MemberInput;
import com.example.zerobaseproject03.member.service.MemberService;
import com.example.zerobaseproject03.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// 관리자 전용 회원 정보에 대한 쿼리를 위한 컨트롤러

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminMemberController {
    private final MemberService memberService;

    // 회원 정보(목록) 페이지 컨트롤러
    // MemberParam => 회원 관리에서 검색한 SearchType,searchValue 속성을 가진다.
    @GetMapping("/member/list.do")
    public String list(Model model, MemberParam parameter) {

        parameter.init();

        List<MemberDto> members = memberService.list(parameter);

        // 페이지 구현 관련 부분
        long totalCount = 0;
        if (members != null && members.size() > 0) {
            totalCount = members.get(0).getTotalCount();
        }

        long pageSize = parameter.getPageSize();
        long pageIndex = parameter.getPageIndex();
        String queryString = parameter.getQueryString();
        PageUtil pageUtil = new PageUtil(totalCount, pageSize, pageIndex, queryString);

        model.addAttribute("members", members);
        model.addAttribute("pager", pageUtil.pager());
        model.addAttribute("totalCount", totalCount);


        return "admin/member/list";
    }


    // 각 회원의 상세페이지에 관한 컨트롤러
    @GetMapping("/member/detail.do")
    public String detail(Model model, MemberParam parameter) {

        parameter.init();


        MemberDto member =
                memberService.detail(parameter.getUserId());

        model.addAttribute("member", member);

        return "admin/member/detail";
    }


    @PostMapping("/member/status.do")
    public String status(Model model, MemberInput parameter) {

        String userId = parameter.getUserId();
        String userStatus = parameter.getUserStatus();


        boolean result = memberService.updateStatus(userId, userStatus);

        return "redirect:/admin/member/detail.do?userId=" + userId;

    }


    @PostMapping("/member/password.do")
    public String updatePassword(Model model, MemberInput parameter) {

        String userId = parameter.getUserId();
        String password = parameter.getPassword();

        boolean result = memberService.updatePassword(userId, password);

        return "redirect:/admin/member/detail.do?userId=" + userId;


    }


}
