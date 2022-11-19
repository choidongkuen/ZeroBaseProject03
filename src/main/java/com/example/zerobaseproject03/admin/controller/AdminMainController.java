package com.example.zerobaseproject03.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 관리자 전용 페이지를 위한 메인 컨트롤러
@Controller
public class AdminMainController {

    @GetMapping("/admin/main.do")
    public String main(){

        return "admin/main";

    }

}
