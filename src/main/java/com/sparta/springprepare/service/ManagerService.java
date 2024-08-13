package com.sparta.springprepare.service;

import com.sparta.springprepare.dto.ManagerDto;
import com.sparta.springprepare.entity.Manager;
import com.sparta.springprepare.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    // 관리자 등록
    public Manager saveManager(ManagerDto managerDto) {
        Long id = managerRepository.save(managerDto);
        return managerRepository.findById(id);
    }

    // 관리자 조회
    public Manager get(Long managerId) {
        return managerRepository.findById(managerId);
    }
}
