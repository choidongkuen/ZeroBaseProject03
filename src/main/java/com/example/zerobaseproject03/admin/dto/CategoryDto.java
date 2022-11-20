package com.example.zerobaseproject03.admin.dto;


import com.example.zerobaseproject03.admin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class CategoryDto {

    private Long id;

    String categoryName;
    int sortValue;
    boolean usingYn;


    // Category List -> CategoryDto List
    public static List<CategoryDto> of(List<Category> categories) {

        if (categories != null) {
            List<CategoryDto> categoryList = new ArrayList<>();
            for (Category c : categories) {
                categoryList.add(of(c));
            }

            return categoryList;
        }

        return null;
    }

    // Category -> CategoryDto
    public static CategoryDto of(Category category) {

        return CategoryDto.builder()
                          .id(category.getId())
                          .categoryName(category.getCategoryName())
                          .sortValue(category.getSortValue())
                          .usingYn(category.isUsingYn())
                          .build();

    }
}
