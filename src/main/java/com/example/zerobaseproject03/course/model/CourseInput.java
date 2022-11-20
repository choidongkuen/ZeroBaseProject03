package com.example.zerobaseproject03.course.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseInput {


    long id;
    long categoryId;

    String subject;
    String keyword;
    String summary;
    String contents;

    long price;
    long salePrice;
    String saleEndDtText;


}
