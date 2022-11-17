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
// 즉, 회원 정보에 관련된 서버로 들어오는 모든 처리에 대한 방향을 서버 앞단에서 제시
// 컨트롤러를 구현한다는 것은 request의 처리를 담당하는 메소드를 구현하는 것이다.
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
    public String registerSubmit(HttpServletRequest request, HttpServletResponse response,
                           com.example.zerobaseproject03.member.model.MemberInput input,
                           Model model) {

        // 회원 정보 실패 or 성공 ?
        boolean result = memberService.register(input);
        // 모델은 컨트롤러가 데이터 처리 후, 뷰에게 전달하는 데이터 객체이다.
        model.addAttribute("result", result);

        return "member/register_complete";

    }

    // 회원가입(get)
    @GetMapping("/register")
    public String register() {

        return "member/register";

    }


    // 이메일 인증 부분(get)
    // 사용자가 이메일 받고 링크 누르면 해당 컨트롤러 실행
    @GetMapping("/email-auth")
    public String emailAuth(HttpServletRequest request, Model model){

        String uuid = request.getParameter("id");

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result",result);


        return "member/email_auth";

    }

    // 사용자 정보 보여주기 요청을 처리하는 컨트롤러 실행

    @GetMapping("/info")
    public String memberInfo(){

        return "member/info";
    }



    // 로그인
    @RequestMapping("/login")
    public String logIn() {

        return "member/login";

    }

}
