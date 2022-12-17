package com.example.SK_Project2.UserService.mapper;

import com.example.SK_Project2.UserService.domain.User;
import com.example.SK_Project2.UserService.domain.UserStatus;
import com.example.SK_Project2.UserService.dto.user.ManagerCreateDto;
import com.example.SK_Project2.UserService.dto.user.ManagerDto;
import com.example.SK_Project2.UserService.repository.RoleRepository;
import com.example.SK_Project2.UserService.repository.UserStatusRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManagerMapper {
    private RoleRepository roleRepository;
    private UserStatusRepository userStatusRepository;

    public ManagerMapper(RoleRepository roleRepository, UserStatusRepository userStatusRepository) {
        this.roleRepository = roleRepository;
        this.userStatusRepository = userStatusRepository;
    }

    public ManagerDto userToManagerDto(User user) {
        ManagerDto managerDto = new ManagerDto();

        managerDto.setId(user.getId());
        managerDto.setUsername(user.getUsername());
        managerDto.setEmail(user.getEmail());
        managerDto.setPhone(user.getPhone());
        managerDto.setDayOfBirth(user.getDayOfBirth());
        managerDto.setFirstName(user.getFirstName());
        managerDto.setLastName(user.getLastName());
        managerDto.setCompanyName(user.getCompanyName());
        managerDto.setEmploymentDay(user.getEmploymentDay());
        managerDto.setForbidden(user.isForbidden());

        return managerDto;
    }

    public User managerCreateDtoToUser(ManagerCreateDto managerCreateDto) {
        User user = new User();

        user.setUsername(managerCreateDto.getUsername());
        user.setPassword(managerCreateDto.getPassword());
        user.setEmail(managerCreateDto.getEmail());
        user.setPhone(managerCreateDto.getPhone());
        user.setDayOfBirth(managerCreateDto.getDayOfBirth());
        user.setFirstName(managerCreateDto.getFirstName());
        user.setLastName(managerCreateDto.getLastName());
        user.setCompanyName(managerCreateDto.getCompanyName());
        user.setEmploymentDay(managerCreateDto.getEmploymentDay());
        user.setForbidden(false);

        user.setPassport(null);
        user.setRentCarTotalDuration(null);
        user.setRole(roleRepository.findRoleByName("ROLE_MANAGER").get());

        return user;
    }
}
