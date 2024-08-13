package com.sparta.springprepare.dto;

import lombok.*;

@Getter
@ToString
public class ScheduleDto {
    private Long scheduleId;
    private String contents;
    private String password;
    private Long managerId;
}
