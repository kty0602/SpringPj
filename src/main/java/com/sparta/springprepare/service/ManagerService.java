package com.sparta.springprepare.service;

import com.sparta.springprepare.dto.ManagerRequestDto;
import com.sparta.springprepare.dto.ManagerResponseDto;
import com.sparta.springprepare.entity.Manager;
import com.sparta.springprepare.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    // 관리자 등록
    public ManagerResponseDto saveManager(ManagerRequestDto managerRequestDto) {
        Long id = managerRepository.save(managerRequestDto);
        Manager manager = managerRepository.findById(id);
        ManagerResponseDto response = new ManagerResponseDto(manager);
        return response;
    }

    // 관리자 조회
    public ManagerResponseDto get(Long managerId) {
        Manager manager = managerRepository.findById(managerId);
        ManagerResponseDto response = new ManagerResponseDto(manager);
        return response;
    }
}
