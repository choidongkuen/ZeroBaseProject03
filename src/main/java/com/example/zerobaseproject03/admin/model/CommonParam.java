package com.example.zerobaseproject03.admin.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data

// 공통 파라미터 기능을 구현하는 클래스

public class CommonParam {

    long pageIndex; // 몇번째 페이지?
    long pageSize; // 페이지 크기

    String searchType;
    String searchValue;

    String userId;

    public long getPageStart(){

        init();
        return (pageIndex - 1) * pageSize;

    }

    // 끝나는 페이지
    public long getPageEnd(){

        init();
        return pageSize;
    }

    public void init(){

        if(pageIndex < 1){
            pageIndex = 1;

        }

        if(pageSize < 10){
            pageSize = 10;
        }

    }

    // 쿼리 문자열 생성하는 메소드(페이지 마다 해당 요청 쿼리 문자열을 가지고 있어야 함)
    public String getQueryString(){

        init();

        StringBuilder sb = new StringBuilder();

        if(searchType != null && searchType.length() > 0){
            sb.append(String.format("searchType=%s",searchType));
        }


        if(searchValue != null && searchValue.length() > 0){

            if(sb.length() > 0){
                sb.append("&");
            }
            sb.append(String.format("searchValue=%s",searchValue));
        }

        return sb.toString();
    }
}
