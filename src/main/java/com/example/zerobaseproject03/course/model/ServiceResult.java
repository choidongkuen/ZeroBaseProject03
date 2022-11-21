package com.example.zerobaseproject03.course.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceResult {

    boolean result;
    String message;

    // 수강 정보 없을 때
    public ServiceResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    // 수강 정보 있을 때

    public ServiceResult(boolean result) {
        this.result = result;
    }
}
