package com.example.zerobaseproject03.member.repository;


import com.example.zerobaseproject03.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {

}
