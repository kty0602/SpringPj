package com.sparta.springprepare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ManagerDto {
    @NotNull
    @NotBlank
    private String managerName;
    @Email
    @NotBlank
    private String email;
}
