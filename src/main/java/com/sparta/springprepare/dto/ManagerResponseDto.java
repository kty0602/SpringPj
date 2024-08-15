package com.sparta.springprepare.dto;

import com.sparta.springprepare.entity.Manager;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ManagerResponseDto {
    private Long managerId;
    private String managerName;
    private String email;
    private LocalDateTime regDate;   // 작성일
    private LocalDateTime modDate;   // 수정일

    public ManagerResponseDto(Manager manager) {
        this.managerId = manager.getManagerId();
        this.managerName = manager.getManagerName();
        this.email = manager.getEmail();
        this.regDate = manager.getRegDate();
        this.modDate = manager.getModDate();
    }
}
