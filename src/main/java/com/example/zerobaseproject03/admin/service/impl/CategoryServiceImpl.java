package com.example.zerobaseproject03.admin.service.impl;

import com.example.zerobaseproject03.admin.dto.CategoryDto;
import com.example.zerobaseproject03.admin.entity.Category;
import com.example.zerobaseproject03.admin.mapper.CategoryMapper;
import com.example.zerobaseproject03.admin.model.CategoryInput;
import com.example.zerobaseproject03.admin.repository.CategoryRepository;
import com.example.zerobaseproject03.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private Sort getSortBySortValue() {

        // Category Respository 에서 데이터를 가져올 때
        // sortValue 기준 내림차순으로 가져오기
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }

    // 카테고리 리스트 저장소로부터 가져오기(Category -> CategoryDto)
    @Override
    public List<CategoryDto> list() {

        List<Category> categories = categoryRepository.findAll(getSortBySortValue());
        return CategoryDto.of(categories);
    }

    // 카테고리 신규 추가
    @Override
    public boolean add(String categoryName) {

        // 카테고리명이 중복되는지 체크하는 로직 필요!


        Category category = Category.builder()
                                    .categoryName(categoryName)
                                    .usingYn(true)
                                    .sortValue(0)
                                    .build();

        categoryRepository.save(category);

        return true;

    }


    // 카테고리 저장소 데이터 수정하기
    @Override
    public boolean update(CategoryInput parameter) {

        Optional<Category> optionalCategory
                = categoryRepository.findById(parameter.getId());

        // 새로운 속성으로 해당 데이터 저장(수정)
        if (optionalCategory.isPresent()) {

            Category category = optionalCategory.get();
            category.setCategoryName(parameter.getCategoryName());
            category.setSortValue(parameter.getSortValue());
            category.setUsingYn(parameter.isUsingYn());

            categoryRepository.save(category);


        }

        return true;

    }


    // 카테고리 저장소에서 삭제하기
    @Override
    public boolean del(Long id) {

        categoryRepository.deleteById(id);

        return true;

    }
    @Override
    public List<CategoryDto> frontList(CategoryDto parameter) {

        return categoryMapper.select(parameter);
    }
}
