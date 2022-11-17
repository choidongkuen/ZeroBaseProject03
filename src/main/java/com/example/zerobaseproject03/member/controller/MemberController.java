package com.example.zerobaseproject03.member.controller;

import com.example.zerobaseproject03.member.service.MemberService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



// Member Controller
// 회원가입/로그인/로그아웃 역할을 분담

@Slf4j
@Controller
@Setter
@Getter
@RequiredArgsConstructor

@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 아이디/비밀번호는 민감한 데이터 -> post 방식
    // 회원가입(post)
    @PostMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response,
                           com.example.zerobaseproject03.member.model.MemberInput input,
                           Model model) {

        boolean result = memberService.register(input);
        // 모델은 컨트롤러가 데이터 처리 후, 뷰에게 전달하는 데이터 객체이다.
        model.addAttribute("result", result);

        return "member/register_complete";

    }

    // 회원가입(get)
    @GetMapping("/register")
    public String registerSubmit() {

        return "member/register";

    }

    // 로그인
    @GetMapping("/login")
    public String logIn() {

        return "member/logIn";

    }

}
