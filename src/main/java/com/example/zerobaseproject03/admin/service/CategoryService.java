package com.example.zerobaseproject03.admin.service;

import com.example.zerobaseproject03.admin.dto.CategoryDto;
import com.example.zerobaseproject03.admin.model.CategoryInput;


import java.util.List;

public interface CategoryService {

    // 카테고리 리스트 가져오기
    List<CategoryDto> list();

    // 카테고리 신규 추가
    boolean add(String categoryName);

    // 카테고리 수정
    boolean update(CategoryInput parameter);

    // 카테고리 삭제
    boolean del(Long id);
}
