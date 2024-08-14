package com.sparta.springprepare.service;

import com.sparta.springprepare.dto.ScheduleDto;
import com.sparta.springprepare.entity.Schedule;
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
    public Schedule saveSchedule(ScheduleDto scheduleDto) {
        Long id = scheduleRepository.save(scheduleDto);
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("일어날 것 같지는 않은 오류 발생"));
    }

    // 선택한 일정 조회
    // 삭제된 일정을 조회하면 데이터가 나오지 않으므로 Exception 처리
    public Schedule get(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("해당 일정이 존재하지 않거나 이미 삭제된 일정입니다."));
    }

    // 전체 일정 조회
    public List<Schedule> findAllList(Long managerId, String modDate, int pageNumber, int pageSize) {
        return scheduleRepository.getList(managerId, modDate, pageNumber, pageSize);
    }

    // 일정 수정
    public Schedule updateSchedule(Long id, ScheduleDto scheduleDto) {
        int i = scheduleRepository.update(id, scheduleDto);
        if(i == 0) {
            throw new PasswordErrorException("비밀번호가 일치하지 않습니다.");
        }
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 일정이 존재하지 않거나 이미 삭제된 일정입니다."));
    }

    // 일정 삭제
    public void deleteSchedule(Long id, ScheduleDto scheduleDto) {
        if(scheduleRepository.isDelete(id)) {
            throw new AlreadyDeleteException();
        }
        int i = scheduleRepository.delete(id, scheduleDto);
        if(i == 0) {
            throw new PasswordErrorException("비밀번호가 틀리거나, 해당 일정이 없습니다.");
        }
    }
}
