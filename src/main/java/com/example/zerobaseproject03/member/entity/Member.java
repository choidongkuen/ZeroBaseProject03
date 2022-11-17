package com.example.zerobaseproject03.member.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity

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
    private LocalDateTime emailAuthDt; // 이메일 인증 날짜

    public Member(String userId, String userName, String phone, String password, LocalDateTime regDt, boolean emailAuthYn, String emailAuthKey, LocalDateTime emailAuthDt) {
        this.userId = userId;
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.regDt = regDt;
        this.emailAuthYn = emailAuthYn;
        this.emailAuthKey = emailAuthKey;
        this.emailAuthDt = emailAuthDt;
    }

    public Member() {
    }

    public static MemberBuilder builder() {
        return new MemberBuilder();
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegDt(LocalDateTime regDt) {
        this.regDt = regDt;
    }

    public void setEmailAuthYn(boolean emailAuthYn) {
        this.emailAuthYn = emailAuthYn;
    }

    public void setEmailAuthKey(String emailAuthKey) {
        this.emailAuthKey = emailAuthKey;
    }

    public void setEmailAuthDt(LocalDateTime emailAuthDt) {
        this.emailAuthDt = emailAuthDt;
    }

    public static class MemberBuilder {
        private String userId;
        private String userName;
        private String phone;
        private String password;
        private LocalDateTime regDt;
        private boolean emailAuthYn;
        private String emailAuthKey;
        private LocalDateTime emailAuthDt;

        MemberBuilder() {
        }

        public MemberBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public MemberBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public MemberBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public MemberBuilder password(String password) {
            this.password = password;
            return this;
        }

        public MemberBuilder regDt(LocalDateTime regDt) {
            this.regDt = regDt;
            return this;
        }

        public MemberBuilder emailAuthYn(boolean emailAuthYn) {
            this.emailAuthYn = emailAuthYn;
            return this;
        }

        public MemberBuilder emailAuthKey(String emailAuthKey) {
            this.emailAuthKey = emailAuthKey;
            return this;
        }

        public MemberBuilder emailAuthDt(LocalDateTime emailAuthDt) {
            this.emailAuthDt = emailAuthDt;
            return this;
        }

        public Member build() {
            return new Member(userId, userName, phone, password, regDt, emailAuthYn, emailAuthKey, emailAuthDt);
        }

        public String toString() {
            return "Member.MemberBuilder(userId=" + this.userId + ", userName=" + this.userName + ", phone=" + this.phone + ", password=" + this.password + ", regDt=" + this.regDt + ", emailAuthYn=" + this.emailAuthYn + ", emailAuthKey=" + this.emailAuthKey + ", emailAuthDt=" + this.emailAuthDt + ")";
        }
    }
}
