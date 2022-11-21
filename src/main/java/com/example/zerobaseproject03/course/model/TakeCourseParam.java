package com.example.zerobaseproject03.course.model;


import com.example.zerobaseproject03.admin.model.CommonParam;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data

// 관리자 회원 관리 페이지에서 검색타입(searchType)& 검색값(searchValue)
// 입력시 사용되는 커맨드 클래스(dao)
public class TakeCourseParam extends CommonParam {

    long id;
    long categoryId;
}
