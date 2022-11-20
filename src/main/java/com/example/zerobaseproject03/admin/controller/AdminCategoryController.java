package com.example.zerobaseproject03.admin.controller;

import com.example.zerobaseproject03.admin.dto.CategoryDto;
import com.example.zerobaseproject03.admin.model.CategoryInput;
import com.example.zerobaseproject03.admin.model.MemberParam;
import com.example.zerobaseproject03.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminCategoryController {

    private final CategoryService categoryService;

    // 관리자 메뉴에서 카테고리 [리스트 가져오기] 컨트롤러
    @GetMapping("/category/list.do")
    public String list(Model model, MemberParam parameter){

        List<CategoryDto> list = categoryService.list();
        model.addAttribute("members",list);

        return "admin/category/list";
    }


    // 관리자 메뉴에서 카테고리 리스트 [추가] 컨트롤러
    @PostMapping("/category/add.do")
    public String add(Model model, CategoryInput parameter){

        boolean result = categoryService.add(parameter.getCategoryName());

        return "redirect:/admin/category/list.do";
    }


    // 관리자 메뉴에서 카테고리 [삭제] 컨트롤러
    @PostMapping("/category/delete.do")
    public String del(Model model, CategoryInput parameter) {

        boolean result = categoryService.del(parameter.getId());

        return "redirect:/admin/category/list.do";

    }

    // 관리자 메뉴에서 카테고리 [수정] 컨트롤러
    @PostMapping("/category/update.do")
    public String update(Model model, CategoryInput parameter) {

        boolean result = categoryService.update(parameter);

        return "redirect:/admin/category/list.do";

    }

}
