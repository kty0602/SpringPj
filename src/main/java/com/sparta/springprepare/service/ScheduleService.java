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

    // 일정 등록
    public Schedule saveSchedule(ScheduleDto scheduleDto) {
        Long id = scheduleRepository.save(scheduleDto);
        return scheduleRepository.findById(id);
    }
}
