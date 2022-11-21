package com.example.zerobaseproject03.course.service.impl;

import com.example.zerobaseproject03.course.dto.TakeCourseDto;
import com.example.zerobaseproject03.course.entity.TakeCourse;
import com.example.zerobaseproject03.course.entity.TakeCourseCode;
import com.example.zerobaseproject03.course.mapper.TakeCourseMapper;
import com.example.zerobaseproject03.course.model.ServiceResult;
import com.example.zerobaseproject03.course.model.TakeCourseParam;
import com.example.zerobaseproject03.course.repository.TakeCourseRepository;
import com.example.zerobaseproject03.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class TakeCourseServiceImpl implements TakeCourseService {


    private final TakeCourseMapper takecourseMapper;
    private final TakeCourseRepository takeCourseRepository;
    private final TakeCourseMapper takeCourseMapper;


    // 회원들이 수강하는 수강 리스트 반환하는 메소드
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


    // 특정 회원의 수강 상태를 변경하는 메소드
    @Override
    public ServiceResult updateStatus(long id, String status) {

        Optional<TakeCourse> optionalTakeCourse
                = takeCourseRepository.findById(id);

        if(optionalTakeCourse.isEmpty()){
            return new ServiceResult(false, "수강 정보가 존재하지 않습니다.");
        }

        TakeCourse takeCourse = optionalTakeCourse.get();
        takeCourse.setStatus(status);
        takeCourseRepository.save(takeCourse);


        return new ServiceResult(true);

    }

    // 해당 id를 가지는 회원의 수강 목록 리스트 반환하는 메소드
    @Override
    public List<TakeCourseDto> myCourse(String userId) {

        TakeCourseParam parameter = new TakeCourseParam();
        parameter.setUserId(userId);
        List<TakeCourseDto> list = takeCourseMapper.selectListMyCourse(parameter);


        return list;
    }


    // 해당 강좌에 내용
    @Override
    public TakeCourseDto detail(long id) {

        Optional<TakeCourse> optionalTakeCourseDto
                = takeCourseRepository.findById(id);

        if(optionalTakeCourseDto.isPresent()){

            return TakeCourseDto.of(optionalTakeCourseDto.get());
        }

        return null;
    }


    // 회원의 강좌 삭제하는 메소드
    @Override
    public ServiceResult cancel(long courseId) {

        Optional<TakeCourse> optionalTakeCourseDto
                = takeCourseRepository.findById(courseId);

        if(optionalTakeCourseDto.isEmpty()){
            return new ServiceResult(false,"수강 정보가 존재하지 않습니다.");
        }

        TakeCourse takeCourse = optionalTakeCourseDto.get();
        takeCourse.setStatus(TakeCourseCode.STATUS_CANCEL); // 해당강좌 취소 상태로 변경
        takeCourseRepository.save(takeCourse); // 다시 저장

        return new ServiceResult(true);

    }
}