package com.sparta.springprepare.controller;

import com.sparta.springprepare.dto.ScheduleDto;
import com.sparta.springprepare.entity.Schedule;
import com.sparta.springprepare.service.ScheduleService;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/register")
    public ResponseEntity<Schedule> saveSchedule(@RequestBody ScheduleDto scheduleDto) {
        Schedule schedule = scheduleService.saveSchedule(scheduleDto);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }
}
