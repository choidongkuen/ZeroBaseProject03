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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    // 저장된 강좌 리스트 리턴하는 메소드
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


    // id를 이용해 해당 강좌 찾는 메소드
    @Override
    public CourseDto getById(long id) {

        return courseRepository.findById(id).
                               map(CourseDto::of).orElse(null);

    }

    // 입력된 할인 종료일을 알맞은 LocalDate 형식으로 바꿔주는 메소드
    private LocalDate getLocalDate(String value) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
           return LocalDate.parse(value, formatter);

        } catch (Exception e) {

        }

        return null;

    }


    // 입력된 강좌 추가하는 메소드
    @Override
    public boolean add(CourseInput parameter) {
        // 2022-11-20

        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());


        Course course = Course.builder()
                  .categoryId(parameter.getCategoryId())
                  .subject(parameter.getSubject())
                  .keyword(parameter.getKeyword())
                  .summary(parameter.getSummary())
                  .contents(parameter.getContents())
                  .price(parameter.getPrice())
                  .salePrice(parameter.getSalePrice())
                  .saleEndDt(saleEndDt)
                  .regDt(LocalDateTime.now())
                  .build();

        courseRepository.save(course);

        return true;
    }


    // 강좌 정보 수정 하는 메소드(edit)
    @Override
    public boolean set(CourseInput parameter) {

        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());


        Optional<Course> optionalCourse =
                courseRepository.findById(parameter.getId());

        if (!optionalCourse.isPresent()) {
            // 수정할 데이터가 존재하지 않음
            return false;
        }

        // 수정할 강좌 엔티티
        Course course = optionalCourse.get();

        course.setCategoryId(parameter.getCategoryId());
        course.setSubject(parameter.getSubject());
        course.setKeyword(parameter.getKeyword());
        course.setSummary(parameter.getSummary());
        course.setContents(parameter.getContents());
        course.setPrice(parameter.getPrice());
        course.setSalePrice(parameter.getSalePrice());
        course.setSaleEndDt(saleEndDt);
        course.setUdtDt(LocalDateTime.now());
        courseRepository.save(course);

        return true;
    }

    // 선택한 강좌 삭제하는 메소드
    @Override
    public boolean del(String idList) {

        // idList => 콤마 단위로 분리된 문자열
        if(idList != null && idList.length() > 0){

            String[] ids = idList.split(",");
            for(String x : ids){
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                }catch (Exception e){

                }

                if(id > 0){

                    courseRepository.deleteById(id);


                }
            }
        }

        return true;
    }
}
