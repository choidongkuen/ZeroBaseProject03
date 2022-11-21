package com.example.zerobaseproject03.member.controller;

import com.example.zerobaseproject03.admin.dto.MemberDto;
import com.example.zerobaseproject03.course.dto.TakeCourseDto;
import com.example.zerobaseproject03.course.model.ServiceResult;
import com.example.zerobaseproject03.course.model.TakeCourseParam;
import com.example.zerobaseproject03.course.service.TakeCourseService;
import com.example.zerobaseproject03.member.model.MemberInput;
import com.example.zerobaseproject03.member.model.ResetPasswordInput;
import com.example.zerobaseproject03.member.service.MemberService;
import com.example.zerobaseproject03.util.PasswordUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;


// Member Controller
// 회원가입/로그인/로그아웃 역할을 분담
// 즉, 회원 정보에 관련된 서버로 들어오는 모든 처리에 대한 방향을 서버 앞단에서 제시
// 컨트롤러를 구현한다는 것은 request의 처리를 담당하는 메소드를 구현하는 것이다.
// 컨트롤러 :: 요청 처리 -> 뷰역할에서 필요한 데이터 모델객체에 담기 -> 뷰 이름 리턴

@Slf4j
@Controller
@Setter
@Getter
@RequiredArgsConstructor

@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final TakeCourseService takeCourseService;

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
    public String emailAuth(HttpServletRequest request, Model model) {

        String uuid = request.getParameter("id");

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);


        return "member/email_auth";

    }

    // 사용자 정보 보여주기 요청을 처리하는 컨트롤러
    @GetMapping("/info")
    public String memberInfo(Model model, Principal principal) {

        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        model.addAttribute( "detail", detail);

        return "member/info";
    }


    // 회원 정보 수정 보내는 컨트롤러
    @PostMapping("/info")
    public String memberInfoSubmit(Model model
            , Principal principal
            , MemberInput parameter) {

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult serviceResult = memberService.updateMember(parameter);

        if(!serviceResult.isResult()){
            model.addAttribute("message",serviceResult.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }


    ///////////////////////
    // 사용자 비밀번호 정보 컨트롤러
    @GetMapping("/password")
    public String memberPassword(Model model, Principal principal) {

        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        model.addAttribute("detail", detail);

        return "member/password";
    }

    // 사용자 비밀번호 정보 컨트롤러
    @PostMapping("/password")
    public String memberPasswordSubmit(Model model,
                                       Principal principal,
                                       MemberInput parameter) {

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult serviceResult =
                memberService.updateMemberPassword(parameter);

        if (!serviceResult.isResult()) {
            model.addAttribute("message", serviceResult.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }

    /////////////////////////
    // 사용자 수강 목록 정보 컨트롤러
    @GetMapping("/takecourse")
    public String memberTakecourse(Model model, Principal principal) {

        String userId = principal.getName();

        List<TakeCourseDto> list =
                takeCourseService.myCourse(userId);

        model.addAttribute("list", list);

        return "member/takecourse";
    }


    // 로그인
    @RequestMapping("/login")
    public String logIn() {

        return "member/login";

    }

    // 비밀번호 찾기(시작)
    @GetMapping("/find/password")
    public String findPassword() {

        return "member/find_password";
    }


    // 비밀번호 찾기(후) -> 초기화 메일을 보내기
    // find_password.html에서 필요한 파라미터 객체 받기
    @PostMapping("/find/password")
    public String findPasswordSubmit(Model model,
                                     ResetPasswordInput resetPasswordInput) {

        boolean result = false;
        try {
            result =
                    memberService.sendResetPassword(resetPasswordInput);
        } catch (Exception e) {

        }
        model.addAttribute("result", result);

        return "member/find_password_result";

    }

    @GetMapping("/reset/password")
    public String resetPassword(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("id");
        // 해당 uuid 값이 초기화하기에 적절한 데이터의 uuid값인지 체크
        boolean result = memberService.checkResetPassword(uuid);

        model.addAttribute("result", result);

        return "member/reset_password";
    }


    @PostMapping("/reset/password")
    public String resetPasswordSubmit(ResetPasswordInput input, Model model) {

        boolean result = false;
        try {
            result = memberService.resetPassword(input.getUserId(), input.getPassword());
        } catch (Exception e) {

        }

        // 실행 결과 리턴(boolean)
        model.addAttribute("result", result);

        return "member/reset_password_result";

    }

    @GetMapping("/withdraw")
    public String memberWithdraw(Model model) {

        return "member/withdraw";
    }

    @PostMapping("/withdraw")
    public String memberWithdraw(Model model,
                         Principal principal,
                                 MemberInput parameter) {

        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        if(!PasswordUtils.equals(parameter.getPassword(), detail.getPassword())){

            model.addAttribute("message",
                    "비밀번호가 일치하지 않습니다.");
            return "common/error";

        }


//        ServiceResult serviceResult = memberService.withdraw(userId);
//
//        if(!serviceResult.isResult()){
//            model.addAttribute("message",serviceResult.getMessage());
//            return "common/error";
//
//        }
//
        return "redirect:/member/logout";

    }
}
