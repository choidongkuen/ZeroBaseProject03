package com.example.zerobaseproject03.common.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 클라이언트 요청에 대한 응답 결과 및 메시지
@Getter
@Setter

public class ResponseResultHeader {

    boolean result;
    String message;

    public ResponseResultHeader(boolean result, String message) {

        this.result = result;
        this.message = message;

    }

    public ResponseResultHeader(boolean result) {

        this.result = result;
    }


}
