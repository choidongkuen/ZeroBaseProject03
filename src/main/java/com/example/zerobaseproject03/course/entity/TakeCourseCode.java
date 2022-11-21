package com.example.zerobaseproject03.course.entity;


// 수강신청을 위한 엔티티
public interface TakeCourseCode {

     String STATUS_REQ = "REQ"; // 수강 신청(결제 전)
     String STATUS_COMPLETE = "COMPLETE"; // 결제 완료
     String STATUS_CANCEL = "CANCEL"; // 수강 취소

}
