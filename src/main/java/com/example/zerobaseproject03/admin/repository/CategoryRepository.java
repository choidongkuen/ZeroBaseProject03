package com.example.zerobaseproject03.admin.repository;


import com.example.zerobaseproject03.admin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


// Category repository 에 대한 다양한 로직 구현하는 인터페이스(JPA)
public interface CategoryRepository extends JpaRepository<Category,Long> {



}
