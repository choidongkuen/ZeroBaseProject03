package com.example.zerobaseproject03.member.service.imp;

import com.example.zerobaseproject03.components.MailComponents;
import com.example.zerobaseproject03.member.entity.Member;
import com.example.zerobaseproject03.member.exception.MemberNotEmailAuthException;
import com.example.zerobaseproject03.member.model.MemberInput;
import com.example.zerobaseproject03.member.repository.MemberRepository;
import com.example.zerobaseproject03.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService {


    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    // 회원가입 로직을 구현하는 메소드(register)
    @Override
    public boolean register(MemberInput input) {

        // 회원가입시 DB에 해당 회원가입 정보가 있는지 체크해야 한다.(Jpa 이용하자)
        Optional<Member> optionalMember =
                memberRepository.findById(input.getUserId());

        if (optionalMember.isPresent()) {
            // 현재 userId 를 가지는 회원 정보가 있는지 확인
            // 이미 해당 회원 정보가 존재하면 return false
            return false;
        }


        // 입력하는 비밀번호의 암호화
        String encPassword =
                BCrypt.hashpw(input.getPassword(),BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString(); // 이메일 인증 키

        Member member = Member.builder()
                              .userId(input.getUserId())
                              .userName(input.getUserName())
                              .phone(input.getPhone())
                              .password(encPassword)
                              .regDt(LocalDateTime.now())
                              .emailAuthYn(false)
                              .emailAuthKey(uuid)
                              .build();


        memberRepository.save(member); // 해당 레코드(Data) 저장

        // 기입한 이메일(아이디)로 메일 전송하기
        String email = input.getUserId();
        String subject = "제로베이스 사이트 가입을 축하드립니다. ";
        String text = "<p>제로베이스 사이트 가입을 축하드립니다.<p><p> 아래 링크를 클릭하셔서 가입을 완료 해주세요.</p>" +
                "<div><a target='_blank' href = 'http://localhost:8080/member/email-auth?id=" + uuid + "'> 가입 완료 </a></div>";

        mailComponents.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember
                = memberRepository.findByEmailAuthKey(uuid);


        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    // userName = 이메일
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        // 이메일 인증이 안된 경우
        if(!member.isEmailAuthYn()){
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 시도해주세요.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        String userId = member.getUserId();
        String password = member.getPassword();

        return new User(userId,password,grantedAuthorities);
    }
}
