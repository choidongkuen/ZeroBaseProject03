package com.example.zerobaseproject03.member.repository;


import com.example.zerobaseproject03.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {
    Optional<Member> findByEmailAuthKey(String emailAuthKey);

}
