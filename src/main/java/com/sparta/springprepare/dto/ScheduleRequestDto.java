package com.sparta.springprepare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter  // 테스트 코드에서 사용하기 위해 추가
@ToString
@Data
public class ScheduleRequestDto {
    @NotNull
    @NotBlank
    @Size(max = 200)
    private String contents;
    @NotNull
    @NotBlank
    private String password;
    private Long managerId;

}
