package com.sparta.springprepare.dto;

import lombok.*;

@Getter
@Setter  // 테스트 코드에서 사용하기 위해 추가
@ToString
public class ScheduleDto {
    private Long scheduleId;
    private String contents;
    private String password;
    private Long managerId;
}
