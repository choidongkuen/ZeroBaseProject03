package com.example.zerobaseproject03.course.repository;


import com.example.zerobaseproject03.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
