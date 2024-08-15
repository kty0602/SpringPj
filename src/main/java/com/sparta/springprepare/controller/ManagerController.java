package com.sparta.springprepare.controller;

import com.sparta.springprepare.dto.ManagerRequestDto;
import com.sparta.springprepare.dto.ManagerResponseDto;
import com.sparta.springprepare.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    // 관리자 등록
    @PostMapping()
    public ResponseEntity<ManagerResponseDto> saveManager(@Valid @RequestBody ManagerRequestDto managerRequestDto) {
        ManagerResponseDto manager = managerService.saveManager(managerRequestDto);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    // 관리자 조회
    @GetMapping("/{managerId}")
    public ResponseEntity<ManagerResponseDto> getManager(@PathVariable("managerId") Long id) {
        ManagerResponseDto manager = managerService.get(id);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

}
