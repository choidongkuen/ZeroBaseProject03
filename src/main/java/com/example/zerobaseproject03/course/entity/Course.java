package com.example.zerobaseproject03.course.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String imagePath;
    String keyword;
    String subject;

    @Column(length = 1000)
    String summary;


    @Lob // Large Object(기본 varchar(255) -> larger Data)
    String contents;
    long price;
    long salePrice;

    LocalDateTime saleEndDt;

    LocalDateTime regDt; // 강좌 추가일
    LocalDateTime udtDt; // 강좌 수정일
}
