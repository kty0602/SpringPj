package com.sparta.springprepare.controller;

import com.sparta.springprepare.dto.ManagerDto;
import com.sparta.springprepare.entity.Manager;
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
    public ResponseEntity<Manager> saveManager(@Valid @RequestBody ManagerDto managerDto) {
        Manager manager = managerService.saveManager(managerDto);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    // 관리자 조회
    @GetMapping("/{managerId}")
    public ResponseEntity<Manager> getManager(@PathVariable("managerId") Long id) {
        Manager manager = managerService.get(id);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

}
