package com.example.zerobaseproject03.course.service.impl;

import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.entity.Course;
import com.example.zerobaseproject03.course.mapper.CourseMapper;
import com.example.zerobaseproject03.course.model.CourseInput;
import com.example.zerobaseproject03.course.model.CourseParam;
import com.example.zerobaseproject03.course.repository.CourseRepository;
import com.example.zerobaseproject03.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    // 입력된 강좌 CourseRepository 저장하는 메소드
    @Override
    public boolean add(CourseInput parameter) {


        Course course = Course.builder()
                              .subject(parameter.getSubject())
                              .regDt(LocalDateTime.now())
                              .build();

        courseRepository.save(course);

        return true;
    }

    @Override
    public List<CourseDto> list(CourseParam parameter) {


        long totalCount = courseMapper.selectListCount(parameter);
        List<CourseDto> list = courseMapper.selectList(parameter);

        // Member 테이블이 비어있지 않다면
        // 구성하는 dto의 totalCount 값 추가
        if (!CollectionUtils.isEmpty(list)) {

            int i = 0;
            for (CourseDto dto : list) {
                dto.setTotalCount(totalCount);
                dto.setSeq(totalCount - parameter.getPageStart() - i);
                i++;

            }
        }
        return list;
    }

    @Override
    public CourseDto getById(long id) {

        return courseRepository.findById(id).
                               map(CourseDto::of).orElse(null);

    }
}
