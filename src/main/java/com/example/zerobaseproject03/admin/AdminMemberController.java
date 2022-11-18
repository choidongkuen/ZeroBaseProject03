package com.example.zerobaseproject03.admin;

import com.example.zerobaseproject03.admin.dto.MemberDto;
import com.example.zerobaseproject03.member.entity.Member;
import com.example.zerobaseproject03.member.service.MemberService;
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
    @GetMapping("/member/list.do")
    public String list(Model model) {

        List<MemberDto> members = memberService.list();
        model.addAttribute("members",members);

        return "admin/member/list";
    }

}
