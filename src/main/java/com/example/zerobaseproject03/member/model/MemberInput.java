package com.example.zerobaseproject03.member.model;


// 커맨드 객채

public class MemberInput {


    private String userId;
    private String userName;
    private String password;
    private String phone;


    public MemberInput(String userId, String userName, String password, String phone) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
    }

    public MemberInput() {
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "MemberInput(userId=" + this.getUserId() + ", userName=" + this.getUserName() + ", password=" + this.getPassword() + ", phone=" + this.getPhone() + ")";
    }
}
