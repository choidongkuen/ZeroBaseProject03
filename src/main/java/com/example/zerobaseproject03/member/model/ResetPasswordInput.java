package com.example.zerobaseproject03.member.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 비밀번호 초기화 전용 커맨드 객체
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResetPasswordInput {

    private String userId;
    private String userName;
    private String password;
}
