package com.sparta.springprepare.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Schedule {
    private Long scheduleId;    // 고유 번호
    private String contents;    // 작업 내용
    private String password;    // 비밀번호
    private Manager manager;      // 관리자
    private LocalDateTime regDate;   // 작성일
    private LocalDateTime modDate;   // 수정일
    private boolean deleteStatus;   // 삭제 여부
}
