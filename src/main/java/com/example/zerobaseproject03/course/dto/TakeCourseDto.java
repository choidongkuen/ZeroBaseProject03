package com.example.zerobaseproject03.course.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class TakeCourseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long courseId;
    String userId;

    long payPrice; // 결제금액
    String status; // 수강신청 대상이 되는 강좌의 상태(수강 신청, 결재 완료, 수강 취소)

    LocalDateTime regDt; // 수강신청일


    // for Join
    String userName;
    String phone;
    String subject;

    long totalCount;
    long seq;


    public String getRegDtText(){

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy.MM.dd / HH:mm");
        return this.regDt != null ? regDt.format(formatter) : "";

    }

}