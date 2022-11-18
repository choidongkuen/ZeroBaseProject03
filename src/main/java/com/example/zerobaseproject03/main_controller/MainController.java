package com.example.zerobaseproject03.main_controller;


// MainPage 클래스를 만든 목적
// 매핑하기 위해서
// 논리적인주소 (인터넷주소) 와 물리적인 파일을 매핑
// => 컨트롤러
// 컨트롤러를 구현한다는 것은 Http 요청을 처리할 메소드를 구현한다는 것


import com.example.zerobaseproject03.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequiredArgsConstructor
// Thymeleaf Controller 에서만 활성화
// Thymeleaf 를 사용하면 return 문자열은 view 파일 이름으로 사용하자(약속)

public class MainController {

    private final MailComponents mailComponents;

    // Main 페이지
    @RequestMapping("/")
    public String index(){

        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied(){

        return "error/denied";
    }

}