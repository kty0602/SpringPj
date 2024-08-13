package com.sparta.springprepare.controller;

import com.sparta.springprepare.dto.ManagerDto;
import com.sparta.springprepare.entity.Manager;
import com.sparta.springprepare.service.ManagerService;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    // 관리자 등록
    @PostMapping("/register")
    public ResponseEntity<Manager> saveManager(@RequestBody ManagerDto managerDto) {
        Manager manager = managerService.saveManager(managerDto);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    // 관리자 조회
    @GetMapping("/read")
    public ResponseEntity<Manager> getManager(@RequestParam Long id) {
        Manager manager = managerService.get(id);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

}
