package com.example.zerobaseproject03.member.service;

import com.example.zerobaseproject03.admin.dto.MemberDto;
import com.example.zerobaseproject03.admin.model.MemberParam;
import com.example.zerobaseproject03.course.model.ServiceResult;
import com.example.zerobaseproject03.member.model.MemberInput;
import com.example.zerobaseproject03.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


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

    // 관리자 페이지에서 회원 목록 리턴하는 메소드(only admin)
    List<MemberDto> list(MemberParam parameter);


    // 각 회원의 상세정보를 가져오는 메소드
    MemberDto detail(String userId);

    // 회원 상태 변경하는 메소드
    boolean updateStatus(String userId, String userStatus);

    // 회원 비밀번호 초기화 하는 메소드
    boolean updatePassword(String userId, String password);


    // 회원 비밀번호 초기화 하는 메소드( 회원 정보 페이지 )
    ServiceResult updateMemberPassword(MemberInput parameter);

}
