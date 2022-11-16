package com.example.zerobaseproject03.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
// 인텔리제이는 따로 저장이 없다.
public class IndexController {

    @GetMapping("/")

    public String index(){

        return "Hello World";
    }
}
