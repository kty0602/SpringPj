package com.sparta.springprepare.service;

import com.sparta.springprepare.dto.ScheduleRequestDto;
import com.sparta.springprepare.dto.ScheduleResponseDto;
import com.sparta.springprepare.exception.AlreadyDeleteException;
import com.sparta.springprepare.exception.NotFoundException;
import com.sparta.springprepare.exception.PasswordErrorException;
import com.sparta.springprepare.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 일정 등록  -> 등록 후 "SELECT LAST_INSERT_ID()"를 통해 등록된 마지막 id값을 리턴 받는다.
    // 이후 findByID를 통해 해당 일정 내용을 가져와서 리턴한다.
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        Long id = scheduleRepository.save(scheduleRequestDto);
        // rowMapper schedule객체에 값을 담아 반환하므로 다시 response에 재분배 (password 제외하고)
        // findById가 Optional를 반환해서 map으로 체이닝
        // 참고 : https://velog.io/@hksdpr/JAVA-Optional%EC%9D%98-%EC%B6%A9%EA%B2%A9%EC%A0%81%EC%9D%B8-%EC%82%AC%EC%9A%A9%EB%B2%95-map%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%B2%B4%EC%9D%B4%EB%8B%9D
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    ScheduleResponseDto response = new ScheduleResponseDto();
                    response.setScheduleId(schedule.getScheduleId());
                    response.setContents(schedule.getContents());
                    response.setManager(schedule.getManager());
                    response.setRegDate(schedule.getRegDate());
                    response.setModDate(schedule.getModDate());
                    response.setDeleteStatus(schedule.isDeleteStatus());
                    return response;
                })
                .orElseThrow(() -> new NotFoundException("일어날 것 같지는 않은 오류 발생"));
    }

    // 선택한 일정 조회
    // 삭제된 일정을 조회하면 데이터가 나오지 않으므로 Exception 처리
    public ScheduleResponseDto get(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .map(schedule -> {
                    ScheduleResponseDto response = new ScheduleResponseDto();
                    response.setScheduleId(schedule.getScheduleId());
                    response.setContents(schedule.getContents());
                    response.setManager(schedule.getManager());
                    response.setRegDate(schedule.getRegDate());
                    response.setModDate(schedule.getModDate());
                    response.setDeleteStatus(schedule.isDeleteStatus());
                    return response;
                })
                .orElseThrow(() -> new NotFoundException("해당 일정이 존재하지 않거나 이미 삭제된 일정입니다."));
    }

    // 전체 일정 조회
    public List<ScheduleResponseDto> findAllList(Long managerId, String modDate, int pageNumber, int pageSize) {
        return scheduleRepository.getList(managerId, modDate, pageNumber, pageSize).stream()
                .map(schedule -> {
                    ScheduleResponseDto response = new ScheduleResponseDto();
                    response.setScheduleId(schedule.getScheduleId());
                    response.setContents(schedule.getContents());
                    response.setManager(schedule.getManager());
                    response.setRegDate(schedule.getRegDate());
                    response.setModDate(schedule.getModDate());
                    response.setDeleteStatus(schedule.isDeleteStatus());
                    return response;
                })
                .toList();
    }

    // 일정 수정
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        int i = scheduleRepository.update(id, scheduleRequestDto);
        if(i == 0) {
            throw new PasswordErrorException("비밀번호가 일치하지 않습니다.");
        }
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    ScheduleResponseDto response = new ScheduleResponseDto();
                    response.setScheduleId(schedule.getScheduleId());
                    response.setContents(schedule.getContents());
                    response.setManager(schedule.getManager());
                    response.setRegDate(schedule.getRegDate());
                    response.setModDate(schedule.getModDate());
                    response.setDeleteStatus(schedule.isDeleteStatus());
                    return response;
                })
                .orElseThrow(() -> new NotFoundException("해당 일정이 존재하지 않거나 이미 삭제된 일정입니다."));
    }

    // 일정 삭제
    public void deleteSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        if(scheduleRepository.isDelete(id)) {
            throw new AlreadyDeleteException();
        }
        int i = scheduleRepository.delete(id, scheduleRequestDto);
        if(i == 0) {
            throw new PasswordErrorException("비밀번호가 틀리거나, 해당 일정이 없습니다.");
        }
    }
}
