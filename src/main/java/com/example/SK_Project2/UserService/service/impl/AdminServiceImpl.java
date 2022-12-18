package com.example.SK_Project2.UserService.service.impl;

import com.example.SK_Project2.UserService.domain.User;
import com.example.SK_Project2.UserService.domain.UserStatus;
import com.example.SK_Project2.UserService.dto.user.*;
import com.example.SK_Project2.UserService.dto.userStatus.RankCreateDto;
import com.example.SK_Project2.UserService.dto.userStatus.RankDto;
import com.example.SK_Project2.UserService.exception.NotFoundException;
import com.example.SK_Project2.UserService.mapper.AdminMapper;
import com.example.SK_Project2.UserService.mapper.RankMapper;
import com.example.SK_Project2.UserService.repository.RoleRepository;
import com.example.SK_Project2.UserService.repository.UserRepository;
import com.example.SK_Project2.UserService.repository.UserStatusRepository;
import com.example.SK_Project2.UserService.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserStatusRepository userStatusRepository;
    private AdminMapper adminMapper;
    private RankMapper rankMapper;

    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                            UserStatusRepository userStatusRepository, AdminMapper adminMapper, RankMapper rankMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userStatusRepository = userStatusRepository;
        this.adminMapper = adminMapper;
        this.rankMapper = rankMapper;
    }

    @Override
    public AdminDto findById(Long id) {
        return userRepository.findById(id)
                .map(adminMapper::userToAdminDto)
                .orElseThrow(()-> new NotFoundException(String.format("Admin with id: %d does not exists.", id)));
    }

    @Override
    public AdminDto update(AdminDto adminDto) {
        User user = userRepository.findById(adminDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d does not exists.", adminDto.getId())));

        user.setId(adminDto.getId());
        user.setUsername(adminDto.getUsername());
        user.setEmail(adminDto.getEmail());
        user.setPhone(adminDto.getPhone());
        user.setDayOfBirth(adminDto.getDayOfBirth());
        user.setFirstName(adminDto.getFirstName());
        user.setLastName(adminDto.getLastName());

        userRepository.save(user);

        return adminMapper.userToAdminDto(user);
    }

    @Override
    public Boolean forbid(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d does not exists.", id)));

        user.setForbidden(true);

        return true;
    }

    @Override
    public RankDto addUserStatusRank(RankCreateDto rankCreateDto) {
        UserStatus rank = rankMapper.rankCreateDtoToUserStatus(rankCreateDto);
        userStatusRepository.save(rank);

        return rankMapper.userStatusToRankDto(rank);
    }

}
