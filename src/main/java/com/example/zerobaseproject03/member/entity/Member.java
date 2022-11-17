package com.example.zerobaseproject03.member.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity // DB의 테이블
@Getter
@Setter
@RequiredArgsConstructor

public class Member {

    @Id
    private String userId; // 사용자 이메일(ID)
    private String userName; // 사용자이름
    private String phone; // 사용자 전화번호
    private String password; // 비밀번호
    private LocalDateTime regDt; // 가입일

    // 회원 가입시, 이메일 인증이 필수
    // 이메일 인증시, 인증코드 생성 => 인증코드가 맞다면 true
    private boolean emailAuthYn; // 이메일 인증 여부
    private String emailAuthKey; // 이메일 인증 키

}
