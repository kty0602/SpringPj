package com.sparta.springprepare.dto;

import com.sparta.springprepare.entity.Manager;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Data
public class ScheduleResponseDto {
    private Long scheduleId;    // 고유 번호
    private String contents;    // 작업 내용
    private Manager manager;      // 관리자
    private LocalDateTime regDate;   // 작성일
    private LocalDateTime modDate;   // 수정일
    private boolean deleteStatus;   // 삭제 여부

}
