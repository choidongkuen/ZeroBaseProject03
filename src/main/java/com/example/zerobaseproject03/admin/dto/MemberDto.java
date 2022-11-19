package com.example.zerobaseproject03.admin.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class MemberDto {

    String userId;
    String userName;
    String phone;
    String password;
    LocalDateTime regDt;

    boolean emailAuthYn;
    LocalDateTime emailAuthDt;
    String emailAuthKey;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDt;
    boolean adminYn;


    // 추가 칼럼

    long totalCount;

}
