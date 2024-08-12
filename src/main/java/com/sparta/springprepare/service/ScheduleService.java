package com.sparta.springprepare.service;

import com.sparta.springprepare.dto.ScheduleDto;
import com.sparta.springprepare.entity.Schedule;
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
        return scheduleRepository.findById(id);
    }

    // 선택한 일정 조회
    public Schedule get(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }

    // 전체 일정 조회
    public List<Schedule> findAllList() {
        return scheduleRepository.getList();
    }

    // 일정 수정
    public Schedule updateSchedule(ScheduleDto scheduleDto) {
        int i = scheduleRepository.update(scheduleDto);
        if(i == 0) {
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }
        return scheduleRepository.findById(scheduleDto.getScheduleId());
    }

    // 일정 삭제
    public void deleteSchedule(ScheduleDto scheduleDto) {
        int i = scheduleRepository.delete(scheduleDto);
        if(i == 0) {
            throw new IllegalStateException("비밀번호가 틀리거나, 해당 일정이 없습니다.");
        }
    }
}
