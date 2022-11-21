package com.example.zerobaseproject03.course.service.impl;

import com.example.zerobaseproject03.course.dto.CourseDto;
import com.example.zerobaseproject03.course.dto.TakeCourseDto;
import com.example.zerobaseproject03.course.entity.Course;
import com.example.zerobaseproject03.course.entity.TakeCourse;
import com.example.zerobaseproject03.course.entity.TakeCourseCode;
import com.example.zerobaseproject03.course.mapper.CourseMapper;
import com.example.zerobaseproject03.course.mapper.TakeCourseMapper;
import com.example.zerobaseproject03.course.model.*;
import com.example.zerobaseproject03.course.repository.CourseRepository;
import com.example.zerobaseproject03.course.repository.TakeCourseRepository;
import com.example.zerobaseproject03.course.service.CourseService;
import com.example.zerobaseproject03.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class TakeCourseServiceImpl implements TakeCourseService {


    private final TakeCourseMapper takecourseMapper;
    private final TakeCourseRepository takeCourseRepository;


    @Override
    public List<TakeCourseDto> list(TakeCourseParam parameter) {

        long totalCount = takecourseMapper.selectListCount(parameter);
        List<TakeCourseDto> list = takecourseMapper.selectList(parameter);

        // Member 테이블이 비어있지 않다면
        // 구성하는 dto의 totalCount 값 추가
        if (!CollectionUtils.isEmpty(list)) {

            int i = 0;
            for (TakeCourseDto dto : list) {
                dto.setTotalCount(totalCount);
                dto.setSeq(totalCount - parameter.getPageStart() - i);
                i++;

            }
        }

        return list;
    }
}