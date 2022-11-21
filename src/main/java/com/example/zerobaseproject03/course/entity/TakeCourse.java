package com.example.zerobaseproject03.course.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 수강신청을 위한 엔티티
public class TakeCourse implements TakeCourseCode {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long courseId;
    String userId;

    long payPrice; // 결제금액
    String status; // 수강신청 대상이 되는 강좌의 상태(수강 신청, 결재 완료, 수강 취소)

    LocalDateTime regDt; // 수강신청일

}
