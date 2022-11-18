package com.example.zerobaseproject03.member.service;

import com.example.zerobaseproject03.member.model.MemberInput;
import com.example.zerobaseproject03.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface MemberService extends UserDetailsService {

    // 해당 커맨트 객체 정보에 따라 DB 저장
    boolean register(MemberInput memberInput);
    // uuid 해당하는 계정을 활성화
    boolean emailAuth(String uuid);
    // 입력한 이메일로 비밀번호 초기화 정보를 전송
    boolean sendResetPassword(ResetPasswordInput resetPasswordInput);

    // 입력받은 uuid에 대해 password로 초기화 진행
    boolean resetPassword(String uuid, String password);


    // 입력받은 uuid 값이 유효한지 확인
    boolean checkResetPassword(String uuid);


}
