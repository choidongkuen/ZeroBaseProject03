package com.example.zerobaseproject03.member.service.imp;

import com.example.zerobaseproject03.admin.dto.MemberDto;
import com.example.zerobaseproject03.admin.mapper.MemberMapper;
import com.example.zerobaseproject03.admin.model.MemberParam;
import com.example.zerobaseproject03.components.MailComponents;
import com.example.zerobaseproject03.course.model.ServiceResult;
import com.example.zerobaseproject03.member.entity.Member;
import com.example.zerobaseproject03.member.exception.MemberNotEmailAuthException;
import com.example.zerobaseproject03.member.exception.MemberStopUserException;
import com.example.zerobaseproject03.member.model.MemberInput;
import com.example.zerobaseproject03.member.model.ResetPasswordInput;
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
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    private final MemberMapper memberMapper;

    //회원가입 로직을 구현하는 메소드(register)
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
                BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString(); // 이메일 인증 키

        Member member = Member.builder()
                              .userId(input.getUserId())
                              .userName(input.getUserName())
                              .phone(input.getPhone())
                              .password(encPassword)
                              .regDt(LocalDateTime.now())
                              .emailAuthYn(false)
                              .emailAuthKey(uuid)
                              .userStatus(Member.MEMBER_STATUS_REQ) // 이메일 인증 전
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

        // 이미 활성화된 상태임으로 더 이상 활성화 x(활성화 실패 메시지)
        if (member.isEmailAuthYn()) {
            return false;
        }

        member.setUserStatus(Member.MEMBER_STATUS_ING); // 이메일 인증 후 상태 변환
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    // userName = 이메일
    // 로그인 관련 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if(Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())){
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 시도해주세요.");

        }

        if(Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())){
            throw new MemberStopUserException("정지된 회원입니다.");

        }

        // 해당 회원이 일반 회원인 경우
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));


        // 해당 회원이 관리자인 경우
        if (member.isAdminYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        String userId = member.getUserId();
        String password = member.getPassword();

        return new User(userId, password, grantedAuthorities);
    }

    // 비밀번호 찾기(using Email,Username)
    @Override
    public boolean sendResetPassword(ResetPasswordInput input) {

        String userId = input.getUserId();
        String username = input.getUserName();

        Optional<Member> optionalMember
                = memberRepository.findByUserIdAndUserName(userId, username);

        // 해당 정보가 없는 경우
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지d 않습니다.");
        }

        Member member = optionalMember.get();
        String uuid = UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1)); // 하루후 까지 유효
        memberRepository.save(member);

        // 해당 정보가 있는 경우
        // 기입한 이메일(아이디)로 메일 전송하기
        String email = input.getUserId();
        String subject = "[제로베이스] 비밀번호 초기화 메일 입니다. ";
        String text = "<p>제로베이스 비밀번호 초기화 메일 입니다.<p>" +
                "<p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>"+
                 "<div><a target='_blank' href='http://localhost:8080/member/reset/password?id=" + uuid + "'> 가입 완료 </a></div>";
        mailComponents.sendMail(email, subject, text);
        return true;

    }


    @Override
    public boolean resetPassword(String uuid, String password) {

        Optional<Member> optionalMember
                = memberRepository.findByResetPasswordKey(uuid);

        // 해당 정보가 없는 경우
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        // 초기화 관련 유효날짜가 지정되지 않은 경우
        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        // 이미 유효날짜가 지난 시점인 경우
        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유요한 날짜가 아닙니다.");
        }


        //  해시화된 암호
        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {


        Optional<Member> optionalMember
                = memberRepository.findByResetPasswordKey(uuid);

        // 해당 정보가 없는 경우
        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        // 초기화 관련 유효날짜가 지정되지 않은 경우
        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        // 이미 유효날짜가 지난 시점인 경우
        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유요한 날짜가 아닙니다.");
        }

        return true;
    }


    // 관리자 페이지에서 회원 관리(회원 목록) 로직
    // MemeberParam parameter =>  입력창에서 받아온 검색 타입과 검색 값
    @Override
    public List<MemberDto> list(MemberParam parameter) {

        long totalCount = memberMapper.selectListCount(parameter);
        List<MemberDto> list = memberMapper.selectList(parameter);


        // Member 테이블이 비어있지 않다면
        // 구성하는 dto의 totalCount 값 추가
        if (!CollectionUtils.isEmpty(list)) {

            int i = 0;
            for (MemberDto dto : list) {
                dto.setTotalCount(totalCount);
                dto.setSeq(totalCount - parameter.getPageStart() - i);
                i++;

            }
        }

        // 모든 회원 목록 리턴
        return list;

    }

    // 회원 목록에서 각 회원의 userId 링크를 타고 각 회원의 정보를 얻어오는 메소드
    // JPA를 통해서 가져오자(쿼리문이 복잡한 것은 myBatis를 쓰자!)
    @Override
    public MemberDto detail(String userId) {

        Optional<Member> optionalMember = memberRepository.findById(userId);

        if (!optionalMember.isPresent()) {
            return null;
        }

        Member member = optionalMember.get();

        return MemberDto.of(member);


    }

    // 회원관리 페이지에서 해당 회원 상태 수정하는 메소드
    @Override
    public boolean updateStatus(String userId, String userStatus) {


        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){

            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        member.setUserStatus(userStatus);
        memberRepository.save(member);

        return true;
    }

    // 회원의 요청으로 비밀번호를 수정하는 메소드(관리자 페이지)
    @Override
    public boolean updatePassword(String userId, String password) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){

            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }


        Member member = optionalMember.get();
        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        member.setPassword(encPassword);
        memberRepository.save(member);

        return true;

    }

    // 회원의 비밀번호를 수정하는 메소드(회원 정보 페이지)
    @Override
    public ServiceResult updateMemberPassword(MemberInput parameter) {

        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);

        // 해당 회원 정보 존재 x
        if(optionalMember.isEmpty()){
            return new ServiceResult(false,"회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        // 전달된 비밀번호가 일치 x
        if(!BCrypt.checkpw(parameter.getPassword(), member.getPassword())){

            return new ServiceResult(false, "비밀번호가 잁치하지 않습니다.");
        }


        String encPassword = BCrypt.hashpw(parameter.getNewPassword(),
                BCrypt.gensalt());

        member.setPassword(encPassword);
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    // 회원 정보 수정하는 메소드(회원 정보 페이지)
    @Override
    public ServiceResult updateMember(MemberInput parameter) {

        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);

        // 해당 회원 정보 존재 x
        if(optionalMember.isEmpty()){
            return new ServiceResult(false,"회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();
        member.setPhone(parameter.getPhone());
        member.setZipCode(parameter.getZipCode());
        member.setAddr(parameter.getAddr());
        member.setAddrDetail(parameter.getAddrDetail());
        member.setUdtDt(LocalDateTime.now());
        memberRepository.save(member);

        return new ServiceResult(true);

    }
}
