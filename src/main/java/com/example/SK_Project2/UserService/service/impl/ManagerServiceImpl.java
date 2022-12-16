package com.example.SK_Project2.UserService.service.impl;

import com.example.SK_Project2.UserService.domain.Role;
import com.example.SK_Project2.UserService.domain.User;
import com.example.SK_Project2.UserService.dto.user.ManagerCreateDto;
import com.example.SK_Project2.UserService.dto.user.ManagerDto;
import com.example.SK_Project2.UserService.exception.NotFoundException;
import com.example.SK_Project2.UserService.mapper.ManagerMapper;
import com.example.SK_Project2.UserService.repository.RoleRepository;
import com.example.SK_Project2.UserService.repository.UserRepository;
import com.example.SK_Project2.UserService.service.ManagerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ManagerMapper managerMapper;

    public ManagerServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ManagerMapper managerMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.managerMapper = managerMapper;
    }

    @Override
    public Page<ManagerDto> findAll(Pageable pageable) {
        List<ManagerDto> managers = new ArrayList<>();
        userRepository.findAll(pageable)
                .forEach( user -> {
                            if (user.getRole().getName().equals("ROLE_MANAGER"))
                                managers.add(managerMapper.userToManagerDto(user));
                        }
                );
        return (Page<ManagerDto>) managers;
    }

    @Override
    public ManagerDto findById(Long id) {
        return userRepository.findById(id)
                .map(managerMapper::userToManagerDto)
                .orElseThrow(()-> new NotFoundException(String.format("Manager with id: %d does not exists.", id)));
    }

    @Override
    public ManagerDto add(ManagerCreateDto managerCreateDto) {
        Role role = roleRepository.findRoleByName("ROLE_MANAGER")
                .orElseThrow(() -> new NotFoundException("Role with name: ROLE_MANAGER not found."));
        User manager = managerMapper.managerCreateDtoToUser(managerCreateDto);
        manager.setRole(role);
        userRepository.save(manager);

        return managerMapper.userToManagerDto(manager);
    }

    @Override
    public Boolean delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d does not exists.", id)));

        userRepository.delete(user);
        return true;
    }

    @Override
    public ManagerDto update(ManagerDto managerDto) {
        User user = userRepository.findById(managerDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d does not exists.", managerDto.getId())));

        user.setId(managerDto.getId());
        user.setUsername(managerDto.getUsername());
        user.setEmail(managerDto.getEmail());
        user.setPhone(managerDto.getPhone());
        user.setDayOfBirth(managerDto.getDayOfBirth());
        user.setFirstName(managerDto.getFirstName());
        user.setLastName(managerDto.getLastName());
        user.setCompanyName(managerDto.getCompanyName());
        user.setEmploymentDay(managerDto.getEmploymentDay());

        userRepository.save(user);

        return managerMapper.userToManagerDto(user);
    }
}
