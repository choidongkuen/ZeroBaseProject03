package com.example.zerobaseproject03.admin;

import com.example.zerobaseproject03.admin.dto.MemberDto;
import com.example.zerobaseproject03.admin.model.MemberParam;
import com.example.zerobaseproject03.member.entity.Member;
import com.example.zerobaseproject03.member.service.MemberService;
import com.example.zerobaseproject03.util.PageUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// 관리자 전용 회원 정보에 대한 쿼리를 위한 컨트롤러

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminMemberController {
    private final MemberService memberService;

    // 회원 정보 페이지
    // MemberParam => 회원 관리에서 검색한 SearchType,searchValue 속성을 가진다.
    @GetMapping("/member/list.do")
    public String list(Model model, MemberParam parameter) {

        parameter.init();

        List<MemberDto> members = memberService.list(parameter);

        // 페이지 구현 관련 부분
        long totalCount = 0;
        if(members != null && members.size() > 0){
            totalCount = members.get(0).getTotalCount();
        }

        long pageSize = parameter.getPageSize();
        long pageIndex = parameter.getPageIndex();
        String queryString = parameter.getQueryString();

        PageUtil pageUtil = new PageUtil(totalCount, pageSize, pageIndex, queryString);

        model.addAttribute("members",members);
        model.addAttribute("pager",pageUtil.pager());
        model.addAttribute("totalCount",totalCount);


        return "admin/member/list";
    }
}
