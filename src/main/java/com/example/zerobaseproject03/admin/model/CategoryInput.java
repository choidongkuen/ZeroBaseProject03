package com.example.zerobaseproject03.admin.model;


// 카테고리 데이터와 관련된 모델


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInput {

    Long id;
    String categoryName;
    int sortValue;
    boolean usingYn;
}
