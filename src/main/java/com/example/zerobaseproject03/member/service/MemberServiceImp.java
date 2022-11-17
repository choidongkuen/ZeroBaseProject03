package com.example.zerobaseproject03.member.service;

import com.example.zerobaseproject03.member.entity.Member;
import com.example.zerobaseproject03.member.model.MemberInput;
import com.example.zerobaseproject03.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService{


    private final MemberRepository memberRepository;

    // 회원가입 로직을 구현하는 메소드(register)
    @Override
    public boolean register(MemberInput memberInput) {

        // 회원가입시 DB에 해당 회원가입 정보가 있는지 체크해야 한다.(Jpa 이용하자)
        Optional<Member> optionalMember =
                memberRepository.findById(memberInput.getUserId());

        if(optionalMember.isPresent()){
            // 현재 userId 를 가지는 회원 정보가 있는지 확인
            return false;
        }

        Member member = new Member();

        member.setUserId(memberInput.getUserId());
        member.setUserName(memberInput.getUserName());
        member.setPhone(memberInput.getPhone());
        member.setPassword(memberInput.getPassword());
        member.setRegDt(LocalDateTime.now());
        memberRepository.save(member); // 해당 레코드(Data) 저장

        return false;

    }
}
