package com.example.zerobaseproject03.course.repository;


import com.example.zerobaseproject03.course.entity.TakeCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TakeCourseRepository extends JpaRepository<TakeCourse, Long> {

    // 해당 강좌 신청이 이미 있는지 없는지 확인
    long countByCourseIdAndUserIdAndStatusIn(long courseId,
                                             String userId,
                                             Collection<String> statusList);
}
