package com.example.SK_Project2.UserService.service;

import com.example.SK_Project2.UserService.dto.user.ManagerCreateDto;
import com.example.SK_Project2.UserService.dto.user.ManagerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerService {
    Page<ManagerDto> findAll(Pageable pageable);

    ManagerDto findById(Long id);

    ManagerDto add(ManagerCreateDto managerCreateDto);

    Boolean delete(Long id);
    ManagerDto update(ManagerDto managerDto);



}
