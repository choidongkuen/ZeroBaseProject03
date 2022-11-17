package com.example.zerobaseproject03.member.service;

import com.example.zerobaseproject03.member.model.MemberInput;



public interface MemberService {

    boolean register(MemberInput memberInput); // 해당 커맨트 객체 정보에 따라 DB 저장
    boolean emailAuth(String uuid); // uuid 해당하는 계정을 활성화


}
