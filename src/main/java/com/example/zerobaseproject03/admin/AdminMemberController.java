package com.example.zerobaseproject03.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 관리자 전용 회원 정보에 대한 쿼리를 위한 컨트롤러
@Controller
@RequestMapping("/admin")
public class AdminMemberController {


    // 회원 정보 페이지
    @GetMapping("/member/list.do")
    public String list(){

        return "admin/member/list";
    }

}
