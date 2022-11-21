package com.example.zerobaseproject03.member.model;


import lombok.*;

// 커맨드 객채
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class MemberInput {


    private String userId;
    private String userName;
    private String password;
    private String phone;
    private String newPassword;

}
