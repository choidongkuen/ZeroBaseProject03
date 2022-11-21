package com.example.zerobaseproject03.common.model;


import lombok.Getter;
import lombok.Setter;


// 수강신청시 응답(Response) 결과 형식 구현
@Getter
@Setter

public class ResponseResult {


    ResponseResultHeader header;
    Object body;

    // 수강신청요청에 대한 비정상적 처리(메시지 필요)
    public ResponseResult(boolean result, String message){
        header = new ResponseResultHeader(result,message);
    }

    // 수강신청요청에 대한 정상적 처리(메세지 필요 x)
    public ResponseResult(boolean result){
        header = new ResponseResultHeader(result);
    }
}
