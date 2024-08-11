package com.sparta.springprepare.service;

import com.sparta.springprepare.dto.ScheduleDto;
import com.sparta.springprepare.entity.Schedule;
import com.sparta.springprepare.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
