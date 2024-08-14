package com.sparta.springprepare.controller;

import com.sparta.springprepare.dto.ScheduleDto;
import com.sparta.springprepare.entity.Schedule;
import com.sparta.springprepare.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 등록
    @PostMapping("/register")
    public ResponseEntity<Schedule> saveSchedule(@Valid @RequestBody ScheduleDto scheduleDto) {
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
    // defaultValue 설정 안하려고 했지만, 데이터가 무수히 많을 때를 생각하면 기본값은 설정되어야지 보기 좋겠다 생각해서 설정
    @GetMapping("/list")
    public ResponseEntity<List<Schedule>> getAllList(
            @RequestParam(required = false) Long managerId,
            @RequestParam(required = false) String modDate,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        List<Schedule> scheduleList = scheduleService.findAllList(managerId, modDate, pageNumber, pageSize);
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    // 일정 수정
    @PatchMapping("/update")
    public ResponseEntity<Schedule> modifySchedule(@RequestBody ScheduleDto scheduleDto) {
        Schedule schedule = scheduleService.updateSchedule(scheduleDto);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 일정 삭제
    @PatchMapping("/delete")
    public ResponseEntity<String> deleteSchedule(@RequestBody ScheduleDto scheduleDto) {
        scheduleService.deleteSchedule(scheduleDto);
        return new ResponseEntity<>("성공적으로 삭제가 되었습니다.", HttpStatus.OK);
    }
}
