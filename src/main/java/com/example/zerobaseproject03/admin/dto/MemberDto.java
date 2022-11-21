package com.example.zerobaseproject03.admin.dto;


import com.example.zerobaseproject03.member.entity.Member;
import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    String userId;
    String userName;
    String phone;
    String password;
    LocalDateTime regDt;
    LocalDateTime udtDt;

    boolean emailAuthYn;
    LocalDateTime emailAuthDt;
    String emailAuthKey;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDt;
    boolean adminYn;
    String userStatus;


    // 추가 칼럼
    long totalCount;
    long seq;


    public static MemberDto of(Member member){

        // builder 패턴으로 객체 생성
        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phone(member.getPhone())
                .regDt(member.getRegDt())
                .udtDt(member.getUdtDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())

                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .adminYn(member.isAdminYn())
                .userStatus(member.getUserStatus())
                .build();

    }


    // 등록일 formatter
    public String getRegDtText(){

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm:ss");
        return this.regDt != null ? regDt.format(formatter) : "";

    }

    // 수정일 formatter

    public String getUdtDtText(){

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm:ss");
        return this.udtDt != null ? udtDt.format(formatter) : "";

    }

}
