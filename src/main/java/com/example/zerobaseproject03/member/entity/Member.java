package com.example.zerobaseproject03.member.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member implements MemberCode {

    @Id
    private String userId; // 사용자 이메일(ID)
    private String userName; // 사용자이름
    private String phone; // 사용자 전화번호
    private String password; // 비밀번호
    private LocalDateTime regDt; // 가입일
    private LocalDateTime udtDt; // 회원 정보 수정

    // 회원 가입시, 이메일 인증이 필수
    // 이메일 인증시, 인증코드 생성 => 인증코드가 맞다면 true

    private boolean emailAuthYn; // 이메일 인증 여부
    private LocalDateTime emailAuthDt; // 이메일 인증 날짜
    private String emailAuthKey; // 이메일 인증 키

    private String resetPasswordKey; // 비밀번호 초기화를 위해
    private LocalDateTime resetPasswordLimitDt; // 비밀번호 key 유효기간

    private boolean adminYn; // 해당 회원(데이터)가 관리자인지 여부

    private String userStatus; // 사용자의 상태(이용 가능 or 정지)

    private String zipCode; // 우편번호
    private String addr; // 일반 주소
    private String addrDetail; // 상세 주소


}