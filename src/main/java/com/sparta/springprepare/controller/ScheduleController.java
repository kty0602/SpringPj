package com.sparta.springprepare.controller;

import com.sparta.springprepare.dto.ScheduleDto;
import com.sparta.springprepare.entity.Schedule;
import com.sparta.springprepare.service.ScheduleService;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 등록
    @PostMapping("/register")
    public ResponseEntity<Schedule> saveSchedule(@RequestBody ScheduleDto scheduleDto) {
        Schedule schedule = scheduleService.saveSchedule(scheduleDto);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 특정 일정 조회  /read?id=조회할 id값
    @GetMapping("/read")
    public ResponseEntity<Schedule> getSchedule(@RequestParam Long id) {
        Schedule schedule = scheduleService.get(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 전체 일정 조회
    @GetMapping("/list")
    public ResponseEntity<List<Schedule>> getAllList() {
        List<Schedule> scheduleList = scheduleService.findAllList();
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    // 일정 수정
    @PatchMapping("/update")
    public ResponseEntity<Schedule> modifySchedule(@RequestBody ScheduleDto scheduleDto) {
        Schedule schedule = scheduleService.updateSchedule(scheduleDto);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

}
