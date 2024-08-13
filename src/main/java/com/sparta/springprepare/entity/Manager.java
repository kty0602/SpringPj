package com.sparta.springprepare.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Manager {
    private Long managerId;
    private String managerName;
    private String email;
    private LocalDateTime regDate;   // 작성일
    private LocalDateTime modDate;   // 수정일
}
