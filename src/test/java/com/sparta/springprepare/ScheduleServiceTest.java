package com.sparta.springprepare;

import com.sparta.springprepare.dto.ScheduleRequestDto;
import com.sparta.springprepare.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ScheduleServiceTest {
    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void insertMultipleSchedulesTest() {
        String password = "1234";
        Long managerId = 1L;

        // 50개의 Schedule 데이터 삽입
        for (int i = 5; i < 55; i++) {
            ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto();
            scheduleRequestDto.setContents("테스트" + i);
            scheduleRequestDto.setPassword(password);
            scheduleRequestDto.setManagerId(managerId);

            scheduleService.saveSchedule(scheduleRequestDto);
        }
    }
}
