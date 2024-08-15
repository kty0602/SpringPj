package com.sparta.springprepare.controller;

import com.sparta.springprepare.dto.ScheduleRequestDto;
import com.sparta.springprepare.dto.ScheduleResponseDto;
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
    @PostMapping()
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto schedule = scheduleService.saveSchedule(scheduleRequestDto);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 특정 일정 조회  /read?id=조회할 id값
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable("scheduleId") Long id) {
        ScheduleResponseDto schedule = scheduleService.get(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 전체 일정 조회
    // defaultValue 설정 안하려고 했지만, 데이터가 무수히 많을 때를 생각하면 기본값은 설정되어야지 보기 좋겠다 생각해서 설정
    @GetMapping()
    public ResponseEntity<List<ScheduleResponseDto>> getAllList(
            @RequestParam(required = false) Long managerId,
            @RequestParam(required = false) String modDate,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        List<ScheduleResponseDto> scheduleList = scheduleService.findAllList(managerId, modDate, pageNumber, pageSize);
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    // 일정 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> modifySchedule(@PathVariable("scheduleId") Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto schedule = scheduleService.updateSchedule(id, scheduleRequestDto);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("scheduleId") Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        scheduleService.deleteSchedule(id, scheduleRequestDto);
        return new ResponseEntity<>("성공적으로 삭제가 되었습니다.", HttpStatus.OK);
    }
}
